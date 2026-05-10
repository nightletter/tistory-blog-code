import { EventPattern, Payload } from '@nestjs/microservices';
import { DynamicConfigService } from './dynamic-config.service';
import { Controller, Logger } from '@nestjs/common';

interface Payload {
  scope: string;
  pattern: string;
  event: string;
}

@Controller()
export class ConfigRefreshConsumer {
  private readonly logger = new Logger(ConfigRefreshConsumer.name);

  constructor(private readonly dynamicConfigService: DynamicConfigService) {}

  @EventPattern('config.refresh')
  async handleRefreshEvent(@Payload() payload: Payload): Promise<void> {
    if (payload.event === 'refresh') {
      await this.dynamicConfigService.load();
      this.logger.log('Config refresh completed successfully');
    }
  }
}
