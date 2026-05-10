import { Injectable, Logger, OnModuleInit } from '@nestjs/common';
import { ConfigClient } from './config.client';
import { ConfigServerPropertySource } from './types';
import { ConfigService } from '@nestjs/config';

@Injectable()
export class DynamicConfigService implements OnModuleInit {
  private readonly logger = new Logger(DynamicConfigService.name);

  constructor(
    private readonly configClient: ConfigClient,
    private readonly configService: ConfigService,
  ) {}

  async onModuleInit(): Promise<void> {
    await this.load();
  }

  async load(): Promise<void> {
    try {
      const data = await this.configClient.load();
      const merged = this.mergePropertySources(data.propertySources ?? []);

      for (const [k, v] of Object.entries(merged)) {
        this.configService.set(this.toEnvKey(k), v);
      }

      this.logger.log(
        `[ConfigServer] loaded ${Object.keys(merged).length} keys`,
      );
    } catch (err) {
      this.logger.error('[ConfigServer] failed to load config', err);
      throw err;
    }
  }

  private mergePropertySources(
    sources: ConfigServerPropertySource[],
  ): Record<string, string> {
    const merged: Record<string, string> = {};
    for (const source of [...sources].reverse()) {
      for (const [key, value] of Object.entries(source.source)) {
        merged[key] = String(value);
      }
    }
    return merged;
  }

  private toEnvKey(key: string): string {
    return key ? key.replaceAll('.', '_').toUpperCase() : '';
  }
}
