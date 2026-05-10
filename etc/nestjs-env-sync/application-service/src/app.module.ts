import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { ConfigModule } from './config/config.module';
import { ConfigServiceProvider } from './config/config-service.provider';

@Module({
  imports: [ConfigModule],
  controllers: [AppController],
  providers: [ConfigServiceProvider],
})
export class AppModule {}
