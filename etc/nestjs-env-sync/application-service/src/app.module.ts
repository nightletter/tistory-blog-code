import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { ConfigModule } from './config/config-module';
import { AppConfig } from './app.config';

@Module({
  imports: [ConfigModule],
  controllers: [AppController],
  providers: [AppConfig],
})
export class AppModule {}
