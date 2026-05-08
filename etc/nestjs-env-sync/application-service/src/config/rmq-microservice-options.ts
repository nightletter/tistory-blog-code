import { MicroserviceOptions, Transport } from '@nestjs/microservices';

const DEFAULT_AMQP_URL = 'amqp://user:password@localhost:5672';

export function createRmqMicroserviceOptions(): MicroserviceOptions {
  const queueName = requireEnv('CONFIG_REFRESH_QUEUE');
  const exchangeName = requireEnv('CONFIG_REFRESH_EXCHANGE');

  return {
    transport: Transport.RMQ,
    options: {
      urls: [process.env.AMQP_URL?.trim() || DEFAULT_AMQP_URL],
      exchange: exchangeName,
      queue: queueName,
      noAck: false,
      queueOptions: {
        durable: true,
      },
    },
  };
}

function requireEnv(name: string): string {
  const value = process.env[name]?.trim();
  if (!value) {
    throw new Error(`${name} is required`);
  }
  return value;
}
