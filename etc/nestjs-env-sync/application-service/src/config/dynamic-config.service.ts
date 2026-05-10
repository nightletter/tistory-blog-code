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
      const data = await this.configClient.fetchEnv();
      const merged = this.mergePropertySources(data.propertySources ?? []);

      for (const [k, v] of Object.entries(merged)) {
        this.configService.set(this.toEnvKey(k), v);
      }

      this.logger.log(
        `[DynamicConfigServer] loaded ${Object.keys(merged).length} keys`,
      );
    } catch (err) {
      this.logger.error('[DynamicConfigServer] failed to load config', err);
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

  /**
   * Spring 설정 키(dot notation)를 Node.js 환경변수 형식으로 변환합니다.
   * @example 'spring.datasource.url' -> 'SPRING_DATASOURCE_URL'
   */
  private toEnvKey(key: string): string {
    return key ? key.replaceAll('.', '_').toUpperCase() : '';
  }
}
