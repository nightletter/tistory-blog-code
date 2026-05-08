import { Controller, Logger } from '@nestjs/common';
import { Ctx, EventPattern, Payload, RmqContext } from '@nestjs/microservices';
import { DynamicConfigService } from './dynamic-config.service';
import { getEnv } from '../utils';

const REFRESH_PATTERN = getEnv('CONFIG_REFRESH_PATTERN', 'config.refresh');

type RefreshPayload = {
  event?: string;
  scope?: string;
  timestamp?: string;
  expiresAt?: string;
};

type RmqChannel = {
  ack: (message: unknown) => void;
  nack: (message: unknown, allUpTo?: boolean, requeue?: boolean) => void;
};

type RmqMessage = {
  fields: {
    deliveryTag: number | string;
  };
  properties: {
    messageId?: string;
    correlationId?: string;
  };
};

@Controller()
export class ConfigRefreshConsumer {
  private readonly logger = new Logger(ConfigRefreshConsumer.name);

  constructor(private readonly dynamicConfigService: DynamicConfigService) {}

  @EventPattern(REFRESH_PATTERN)
  async handleRefreshEvent(
    @Payload() payload: unknown,
    @Ctx() context: RmqContext,
  ): Promise<void> {
    const channel = this.getChannel(context);
    const message = this.getMessage(context);
    const messageId = this.getMessageId(context);
    try {
      const event = this.toRefreshPayload(this.parsePayload(payload));
      if (event.event && event.event !== 'refresh') {
        this.logger.debug(
          `[AMQP] skip event [${messageId}] - unsupported type: ${event.event ?? 'unknown'}`,
        );
        channel.ack(message);
        return;
      }

      if (event.expiresAt) {
        const expiresAt = Date.parse(event.expiresAt);
        if (Number.isNaN(expiresAt)) {
          this.logger.warn(
            `[AMQP] skip event [${messageId}] - invalid expiresAt: ${event.expiresAt}`,
          );
          channel.ack(message);
          return;
        }
        if (expiresAt <= Date.now()) {
          this.logger.debug(`[AMQP] skip event [${messageId}] - expired`);
          channel.ack(message);
          return;
        }
      }

      this.logger.log(
        `[AMQP] refresh event received [${messageId}] scope=${event.scope ?? 'unknown'}`,
      );
      await this.dynamicConfigService.load();
      channel.ack(message);
    } catch (err) {
      this.logger.error(`[AMQP] consume error [${messageId}]`, err);
      channel.nack(message, false, false);
    }
  }

  private parsePayload(payload: unknown): Record<string, unknown> {
    if (payload === null || payload === undefined) {
      return {};
    }

    if (typeof payload === 'string') {
      const raw = payload.trim();
      if (!raw) {
        return {};
      }

      let parsed: unknown;
      try {
        parsed = JSON.parse(raw);
      } catch {
        throw new Error(`Message body is not valid JSON: ${raw}`);
      }

      if (!parsed || Array.isArray(parsed) || typeof parsed !== 'object') {
        throw new Error('Message body must be a JSON object');
      }

      return parsed as Record<string, unknown>;
    }

    if (typeof payload !== 'object' || Array.isArray(payload)) {
      throw new Error('Message body must be a JSON object');
    }

    return payload as Record<string, unknown>;
  }

  private toRefreshPayload(payload: Record<string, unknown>): RefreshPayload {
    const event =
      this.asString(payload['event']) ??
      this.parseSpringCloudBusEventType(payload['type']);

    return {
      event,
      scope:
        this.asString(payload['scope']) ??
        this.asString(payload['originService']) ??
        this.asString(payload['destinationService']),
      timestamp: this.asString(payload['timestamp']),
      expiresAt: this.asString(payload['expiresAt']),
    };
  }

  private getMessageId(context: RmqContext): string {
    const message = this.getMessage(context);
    const messageId = this.getMessageProperty(context, 'messageId');
    if (messageId) {
      return messageId;
    }

    const correlationId = this.getMessageProperty(context, 'correlationId');
    if (correlationId) {
      return correlationId;
    }

    return String(message.fields.deliveryTag);
  }

  private getMessageProperty(
    context: RmqContext,
    key: 'messageId' | 'correlationId',
  ): string | undefined {
    const message = this.getMessage(context);
    if (
      key === 'messageId' &&
      typeof message.properties.messageId === 'string' &&
      message.properties.messageId
    ) {
      return message.properties.messageId;
    }

    if (
      key === 'correlationId' &&
      typeof message.properties.correlationId === 'string' &&
      message.properties.correlationId
    ) {
      return message.properties.correlationId;
    }

    return undefined;
  }

  private asString(value: unknown): string | undefined {
    return typeof value === 'string' ? value : undefined;
  }

  private parseSpringCloudBusEventType(value: unknown): string | undefined {
    if (typeof value !== 'string') {
      return undefined;
    }
    return value.toLowerCase().includes('refresh') ? 'refresh' : value;
  }

  private getChannel(context: RmqContext): RmqChannel {
    return context.getChannelRef() as RmqChannel;
  }

  private getMessage(context: RmqContext): RmqMessage {
    return context.getMessage() as RmqMessage;
  }
}
