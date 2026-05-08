import { Controller, Logger } from '@nestjs/common';
import {
  Ctx,
  EventPattern,
  MessagePattern,
  Payload,
  RmqContext,
} from '@nestjs/microservices';
import { DynamicConfigService } from './dynamic-config.service';
import { getEnv } from '../utils';

@Controller()
export class ConfigRefreshConsumer {
  private readonly logger = new Logger(ConfigRefreshConsumer.name);
  private readonly pattern = getEnv('CONFIG_REFRESH_PATTERN', 'config.refresh');

  constructor(private readonly dynamicConfigService: DynamicConfigService) {}

  @EventPattern('config.refresh')
  async handleRefreshEvent(
    @Payload() payload: any,
    @Ctx() context: RmqContext,
  ): Promise<void> {
    this.logger.log('ConfigRefresh complete');
  }
}
