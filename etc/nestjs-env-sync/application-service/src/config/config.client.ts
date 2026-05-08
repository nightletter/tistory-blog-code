import axios from 'axios';
import { Injectable } from '@nestjs/common';
import { ConfigServerResponse } from './types';

const configServerUrl = 'http://localhost:8888';
const appName = 'application';
const profile = 'dev';

const url = `${configServerUrl}/${appName}/${profile}`;

@Injectable()
export class ConfigClient {
  async load(): Promise<ConfigServerResponse> {
    const { data } = await axios.get<ConfigServerResponse>(url);
    return data;
  }
}
