import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';

@Injectable()
export class ConfigServiceProvider {
  constructor(private configService: ConfigService) {}

  requireEnv(k: string) {
    const v = this.configService.get<string>(k);
    if (!v) {
      throw new Error(`Environment variable "${k}" is missing or empty`);
    }
    return v;
  }
}
