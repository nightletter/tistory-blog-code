import axios from 'axios';
import { Injectable } from '@nestjs/common';
import { ConfigServerResponse } from './types';
import { ConfigServiceProvider } from './config-service.provider';

@Injectable()
export class ConfigClient {
  constructor(private readonly config: ConfigServiceProvider) {}

  async load(): Promise<ConfigServerResponse> {
    const configServerUrl = this.config.requireEnv('CONFIG_SERVER_URL');
    const applicationName = this.config.requireEnv('APPLICATION_NAME');
    const profile = this.config.requireEnv('ENV');

    const url = `${configServerUrl}/${applicationName}/${profile}`;
    const { data } = await axios.get<ConfigServerResponse>(url);
    return data;
  }
}
