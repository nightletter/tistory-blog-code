import { Module } from '@nestjs/common';
import { ConfigModule as NestConfigModule } from '@nestjs/config';
import { DynamicConfigService } from './dynamic-config.service';
import { ConfigRefreshConsumer } from './config-refresh.consumer';
import { ConfigClient } from './config.client';
import { ConfigServiceProvider } from './config-service.provider';

@Module({
  imports: [
    NestConfigModule.forRoot({
      isGlobal: true,
      cache: false,
    }),
  ],
  controllers: [ConfigRefreshConsumer],
  providers: [DynamicConfigService, ConfigClient, ConfigServiceProvider],
  exports: [DynamicConfigService],
})
export class ConfigModule {}
