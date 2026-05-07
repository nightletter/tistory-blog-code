import {
  Injectable,
  Logger,
  OnModuleDestroy,
  OnModuleInit,
} from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { Channel, ChannelModel, ConsumeMessage, connect } from 'amqplib';
import { DynamicConfigService } from './dynamic-config.service';

type RefreshPayload = {
  event?: string;
  scope?: string;
  timestamp?: string;
  expiresAt?: string;
};

@Injectable()
export class ConfigRefreshConsumer implements OnModuleInit, OnModuleDestroy {
  private readonly logger = new Logger(ConfigRefreshConsumer.name);
  private connection?: ChannelModel;
  private channel?: Channel;
  private consumerTag?: string;

  constructor(
    private readonly dynamicConfigService: DynamicConfigService,
    private readonly configService: ConfigService,
  ) {}

  async onModuleInit(): Promise<void> {
    const amqpUrl = this.configService.get<string>('AMQP_URL')?.trim();
    const queueName = this.requireConfig('CONFIG_REFRESH_QUEUE');
    const exchangeName = this.configService
      .get<string>('CONFIG_REFRESH_EXCHANGE')
      ?.trim();
    const routingKey =
      this.configService.get<string>('CONFIG_REFRESH_ROUTING_KEY')?.trim() ??
      '';

    this.connection = await connect(
      amqpUrl || 'amqp://user:password@localhost:5672',
    );
    this.connection.on('error', (err) => {
      this.logger.error('[AMQP] connection error', err);
    });
    this.connection.on('close', () => {
      this.logger.warn('[AMQP] connection closed');
    });

    this.channel = await this.connection.createChannel();
    await this.channel.assertQueue(queueName, { durable: true });

    if (exchangeName) {
      await this.channel.assertExchange(exchangeName, 'topic', {
        durable: true,
      });
      await this.channel.bindQueue(queueName, exchangeName, routingKey);
    }

    const consumeResult = await this.channel.consume(
      queueName,
      (message) => {
        void this.handleMessage(message);
      },
      { noAck: false },
    );

    this.consumerTag = consumeResult.consumerTag;
    this.logger.log(
      `[AMQP] consumer started queue=${queueName}${exchangeName ? ` exchange=${exchangeName} routingKey=${routingKey || '(empty)'}` : ''}`,
    );
  }

  async onModuleDestroy(): Promise<void> {
    if (this.channel && this.consumerTag) {
      await this.channel.cancel(this.consumerTag);
    }
    await this.channel?.close();
    await this.connection?.close();
  }

  private async handleMessage(message: ConsumeMessage | null): Promise<void> {
    if (!message) {
      return;
    }

    const channel = this.getChannel();
    const messageId = this.getMessageId(message);

    try {
      const event = this.toRefreshPayload(this.parsePayload(message));
      if (event.event !== 'refresh') {
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

  private parsePayload(message: ConsumeMessage): Record<string, unknown> {
    const raw = message.content.toString('utf-8').trim();
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

  private getMessageId(message: ConsumeMessage): string {
    return (
      message.properties.messageId ||
      message.properties.correlationId ||
      String(message.fields.deliveryTag)
    );
  }

  private getChannel(): Channel {
    if (!this.channel) {
      throw new Error('AMQP channel is not initialized');
    }
    return this.channel;
  }

  private requireConfig(name: string): string {
    const value = this.configService.get<string>(name)?.trim();
    if (!value) {
      throw new Error(`${name} is required`);
    }
    return value;
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
}
