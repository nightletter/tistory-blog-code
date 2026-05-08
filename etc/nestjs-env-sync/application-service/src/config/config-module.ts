import { Module } from '@nestjs/common';
import { ConfigModule as NestConfigModule } from '@nestjs/config';
import { DynamicConfigService } from './dynamic-config.service';
import { ConfigRefreshConsumer } from './config-refresh.consumer';
import { ConfigClient } from './config.client';

@Module({
  imports: [NestConfigModule.forRoot({ isGlobal: true })],
  controllers: [ConfigRefreshConsumer],
  providers: [DynamicConfigService, ConfigClient],
  exports: [DynamicConfigService],
})
export class ConfigModule {}
