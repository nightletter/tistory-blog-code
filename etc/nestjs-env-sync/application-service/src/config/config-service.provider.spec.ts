import { ConfigService } from '@nestjs/config';
import { ConfigServiceProvider } from './config-service.provider';

describe('ConfigServiceProvider', () => {
  let configService: jest.Mocked<Pick<ConfigService, 'get'>>;
  let provider: ConfigServiceProvider;

  beforeEach(() => {
    configService = {
      get: jest.fn(),
    };
    provider = new ConfigServiceProvider(
      configService as unknown as ConfigService,
    );
  });

  it('환경변수가 존재하면 값을 반환한다', () => {
    configService.get.mockReturnValue('http://localhost:8888');

    const result = provider.requireEnv('CONFIG_SERVER_URL');

    expect(result).toBe('http://localhost:8888');
    expect(configService.get).toHaveBeenCalledWith('CONFIG_SERVER_URL');
  });

  it('환경변수가 없으면 에러를 던진다', () => {
    configService.get.mockReturnValue(undefined);

    expect(() => provider.requireEnv('CONFIG_SERVER_URL')).toThrow(
      'Environment variable "CONFIG_SERVER_URL" is missing or empty',
    );
  });

  it('환경변수가 빈 문자열이면 에러를 던진다', () => {
    configService.get.mockReturnValue('');

    expect(() => provider.requireEnv('CONFIG_SERVER_URL')).toThrow(
      'Environment variable "CONFIG_SERVER_URL" is missing or empty',
    );
  });
});
