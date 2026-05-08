import { Injectable } from '@nestjs/common';
import { getEnv, requireEnv } from './utils';

@Injectable()
export class AppConfig {
  public port: number;
  public amqpUrl: string;
  public configRefreshQueue: string;
  public configRefreshExchange: string;
  public configRefreshRoutingKey: string;
  public configRefreshPattern: string;

  private readonly dynamicStorage = new Map<string, unknown>();

  constructor() {
    this.port = Number(getEnv('PORT', '3000'));
    this.amqpUrl = getEnv('AMQP_URL', 'amqp://user:password@localhost:5672');
    this.configRefreshQueue = requireEnv('CONFIG_REFRESH_QUEUE');
    this.configRefreshExchange = requireEnv('CONFIG_REFRESH_EXCHANGE');
    this.configRefreshRoutingKey = requireEnv('CONFIG_REFRESH_ROUTING_KEY');
    this.configRefreshPattern = getEnv(
      'CONFIG_REFRESH_PATTERN',
      'config.refresh',
    );
  }

  update(payload: Record<string, unknown>): void {
    for (const [key, value] of Object.entries(payload)) {
      switch (key) {
        case 'port': {
          const port = Number(value);
          if (!Number.isNaN(port)) {
            this.port = port;
          }
          break;
        }
        case 'amqpUrl':
          if (typeof value === 'string') {
            this.amqpUrl = value;
          }
          break;
        case 'configRefreshQueue':
          if (typeof value === 'string') {
            this.configRefreshQueue = value;
          }
          break;
        case 'configRefreshExchange':
          if (typeof value === 'string') {
            this.configRefreshExchange = value;
          }
          break;
        case 'configRefreshRoutingKey':
          if (typeof value === 'string') {
            this.configRefreshRoutingKey = value;
          }
          break;
        case 'configRefreshPattern':
          if (typeof value === 'string') {
            this.configRefreshPattern = value;
          }
          break;
        default:
          this.dynamicStorage.set(key, value);
      }
    }
  }

  getDynamic<T>(key: string, fallback?: T): T | undefined {
    return this.dynamicStorage.has(key)
      ? (this.dynamicStorage.get(key) as T)
      : fallback;
  }
}
