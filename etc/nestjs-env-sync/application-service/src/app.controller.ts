import { Controller, Get } from '@nestjs/common';
import { ConfigServiceProvider } from './config/config-service.provider';

@Controller()
export class AppController {
  constructor(private readonly config: ConfigServiceProvider) {}

  @Get()
  getHello(): string {
    return this.config.requireEnv('APPLICATION_GREETING_MESSAGE');
  }
}
