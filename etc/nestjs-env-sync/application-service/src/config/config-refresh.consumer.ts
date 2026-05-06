import {
  Injectable,
  Logger,
  OnModuleInit,
  OnModuleDestroy,
} from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import Redis from 'ioredis';
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
  private readonly consumerName = `application-${process.pid}`;
  private redis?: Redis;
  private running = false;

  constructor(
    private readonly dynamicConfigService: DynamicConfigService,
    private readonly configService: ConfigService,
  ) {}

  async onModuleInit() {
    const streamKey = this.requireConfig('CONFIG_STREAM_KEY');
    const groupName = this.requireConfig('CONFIG_CONSUME_GROUP');
    this.redis = new Redis({
      host: this.configService.get<string>('REDIS_HOST') ?? 'localhost',
      port: this.readRedisPort(),
    });

    await this.ensureGroup(streamKey, groupName);
    this.running = true;
    this.logger.log(
      `[Stream] consumer started stream=${streamKey} group=${groupName} consumer=${this.consumerName}`,
    );
    void this.poll(streamKey, groupName);
  }

  onModuleDestroy() {
    this.running = false;
    this.redis?.disconnect();
  }

  private async ensureGroup(streamKey: string, groupName: string) {
    const redis = this.getRedis();

    try {
      await redis.xgroup('CREATE', streamKey, groupName, '$', 'MKSTREAM');
    } catch (err: unknown) {
      if (!(err instanceof Error) || !err.message.includes('BUSYGROUP')) {
        throw err;
      }
    }
  }

  private async poll(streamKey: string, groupName: string) {
    const redis = this.getRedis();

    while (this.running) {
      try {
        const results = (await redis.xreadgroup(
          'GROUP',
          groupName,
          this.consumerName,
          'COUNT',
          '10',
          'BLOCK',
          '5000',
          'STREAMS',
          streamKey,
          '>',
        )) as Array<[string, Array<[string, string[]]>]> | null;

        if (!results) continue;

        for (const [, messages] of results) {
          for (const [id, fields] of messages) {
            await this.handleMessage(id, this.parseFields(fields));
            await redis.xack(streamKey, groupName, id);
          }
        }
      } catch (err) {
        this.logger.error('[Stream] consume error', err);
        await this.sleep(3000);
      }
    }
  }

  private async handleMessage(id: string, payload: Record<string, string>) {
    const event = this.toRefreshPayload(payload);
    if (event.event !== 'refresh') {
      this.logger.debug(
        `[Stream] skip event [${id}] - unsupported type: ${event.event ?? 'unknown'}`,
      );
      return;
    }

    if (event.expiresAt) {
      const expiresAt = Date.parse(event.expiresAt);
      if (Number.isNaN(expiresAt)) {
        this.logger.warn(
          `[Stream] skip event [${id}] - invalid expiresAt: ${event.expiresAt}`,
        );
        return;
      }
      if (expiresAt <= Date.now()) {
        this.logger.debug(`[Stream] skip event [${id}] - expired`);
        return;
      }
    }

    this.logger.log(
      `[Stream] refresh event received [${id}] scope=${event.scope ?? 'unknown'}`,
    );
    await this.dynamicConfigService.load();
  }

  private parseFields(fields: string[]): Record<string, string> {
    const result: Record<string, string> = {};
    for (let i = 0; i < fields.length; i += 2) {
      result[fields[i]] = fields[i + 1];
    }
    return result;
  }

  private toRefreshPayload(payload: Record<string, string>): RefreshPayload {
    return {
      event: payload['event'],
      scope: payload['scope'],
      timestamp: payload['timestamp'],
      expiresAt: payload['expiresAt'],
    };
  }

  private getRedis(): Redis {
    if (!this.redis) {
      throw new Error('Redis client is not initialized');
    }
    return this.redis;
  }

  private requireConfig(name: string): string {
    const value = this.configService.get<string>(name)?.trim();
    if (!value) {
      throw new Error(`${name} is required`);
    }
    return value;
  }

  private readRedisPort(): number {
    const rawPort = this.configService.get<string>('REDIS_PORT');
    const port = Number(rawPort ?? 6379);
    if (!Number.isInteger(port) || port <= 0) {
      throw new Error(`REDIS_PORT is invalid: ${rawPort ?? ''}`);
    }
    return port;
  }

  private sleep = (ms: number) => new Promise((r) => setTimeout(r, ms));
}
