import { NestFactory } from '@nestjs/core';
import { MicroserviceOptions } from '@nestjs/microservices';
import { AppModule } from './app.module';
import { AppConfig } from './app.config';
import { createRmqMicroserviceOptions } from './config/rmq-microservice-options';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  const appConfig = app.get(AppConfig);
  app.connectMicroservice<MicroserviceOptions>(
    createRmqMicroserviceOptions(appConfig),
  );
  await app.startAllMicroservices();
  await app.listen(appConfig.port);
}

void bootstrap();
