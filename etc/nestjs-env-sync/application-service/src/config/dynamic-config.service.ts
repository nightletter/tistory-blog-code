import { Injectable, Logger, OnModuleInit } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import axios from 'axios';

type ConfigServerPropertySource = {
  source: Record<string, unknown>;
};

type ConfigServerResponse = {
  propertySources?: ConfigServerPropertySource[];
};

@Injectable()
export class DynamicConfigService implements OnModuleInit {
  private readonly logger = new Logger(DynamicConfigService.name);

  constructor(private readonly configService: ConfigService) {}

  async onModuleInit(): Promise<void> {
    await this.load();
  }

  async load(): Promise<void> {
    const configServerUrl = this.get(
      'CONFIG_SERVER_URL',
      'http://localhost:8888',
    );
    const appName = this.get('SPRING_APPLICATION_NAME', 'application');
    const profile = this.get('SPRING_PROFILES_ACTIVE', 'dev');
    const url = `${configServerUrl}/${appName}/${profile}`;

    try {
      const { data } = await axios.get<ConfigServerResponse>(url);
      const merged = this.mergePropertySources(data.propertySources ?? []);

      for (const [k, v] of Object.entries(merged)) {
        process.env[this.toEnvKey(k)] = v;
      }

      this.logger.log(
        `[ConfigServer] loaded ${Object.keys(merged).length} keys`,
      );
    } catch (err) {
      this.logger.error('[ConfigServer] failed to load config', err);
      throw err;
    }
  }

  private get(key: string, fallback: string): string {
    return this.configService.get<string>(key) ?? fallback;
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
