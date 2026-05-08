import { MicroserviceOptions, Transport } from '@nestjs/microservices';
import { requireEnv } from '../utils';

export function createRmqMicroserviceOptions(): MicroserviceOptions {
  return {
    transport: Transport.RMQ,
    options: {
      urls: [requireEnv('AMQP_URL')],
      queue: requireEnv('CONFIG_REFRESH_QUEUE'),
      exchange: requireEnv('CONFIG_REFRESH_EXCHANGE'),
      routingKey: requireEnv('CONFIG_REFRESH_ROUTING_KEY'),
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
