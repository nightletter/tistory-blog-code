import { MicroserviceOptions, Transport } from '@nestjs/microservices';
import { AppConfig } from '../app.config';

export function createRmqMicroserviceOptions(
  appConfig: AppConfig,
): MicroserviceOptions {
  return {
    transport: Transport.RMQ,
    options: {
      urls: [appConfig.amqpUrl],
      exchange: appConfig.configRefreshExchange,
      routingKey: appConfig.configRefreshRoutingKey,
      queue: appConfig.configRefreshQueue,
      noAck: false,
      socketOptions: {
        heartbeatIntervalInSeconds: 0,
      },
      queueOptions: {
        durable: true,
      },
    },
  };
}
