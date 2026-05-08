import { NestFactory } from '@nestjs/core';
import { MicroserviceOptions } from '@nestjs/microservices';
import { AppModule } from './app.module';
import { createRmqMicroserviceOptions } from './config/rmq-microservice-options';
import { getEnv } from './utils';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.connectMicroservice<MicroserviceOptions>(createRmqMicroserviceOptions());
  await app.startAllMicroservices();
  const port = Number(getEnv('PORT', '3000'));
  await app.listen(port);
}

void bootstrap();
