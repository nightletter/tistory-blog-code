import { Module } from '@nestjs/common';
import { ConfigModule as NestConfigModule } from '@nestjs/config';
import { DynamicConfigService } from './dynamic-config.service';
import { ConfigRefreshConsumer } from './config-refresh.consumer';

@Module({
  imports: [NestConfigModule.forRoot({ isGlobal: true })],
  providers: [DynamicConfigService, ConfigRefreshConsumer],
  exports: [DynamicConfigService],
})
export class ConfigModule {}
