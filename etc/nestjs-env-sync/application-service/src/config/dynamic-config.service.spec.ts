import { ConfigService } from '@nestjs/config';
import { ConfigClient } from './config.client';
import { DynamicConfigService } from './dynamic-config.service';

describe('DynamicConfigService', () => {
  let configClient: jest.Mocked<Pick<ConfigClient, 'fetchEnv'>>;
  let configService: jest.Mocked<Pick<ConfigService, 'set'>>;
  let service: DynamicConfigService;

  beforeEach(() => {
    configClient = {
      fetchEnv: jest.fn(),
    };
    configService = {
      set: jest.fn(),
    };

    service = new DynamicConfigService(
      configClient as unknown as ConfigClient,
      configService as unknown as ConfigService,
    );
  });

  it('설정값 로드 시 propertySources 배열 순서에 따라 우선순위가 올바르게 적용되는지 확인한다', async () => {
    configClient.fetchEnv.mockResolvedValue({
      propertySources: [
        {
          source: {
            'application.greeting.message': 'test',
            'high.or.low': 'high',
          },
        },
        {
          source: {
            'high.or.low': 'low',
            'feature.flag': true,
            'request.timeout': 30,
          },
        },
      ],
    });

    await service.load();

    expect(configService.set).toHaveBeenCalledTimes(4);
    expect(configService.set.mock.calls).toEqual(
      expect.arrayContaining([
        ['APPLICATION_GREETING_MESSAGE', 'test'],
        ['HIGH_OR_LOW', 'high'],
        ['FEATURE_FLAG', 'true'],
        ['REQUEST_TIMEOUT', '30'],
      ]),
    );
  });

  it('fetchEnv 에러 시, 로깅 테스트', async () => {
    const err = new Error('config server unavailable');
    configClient.fetchEnv.mockRejectedValueOnce(err);
    const logger = Reflect.get(service, 'logger') as {
      error: (message: string, error: Error) => void;
    };
    const loggerErrorSpy = jest
      .spyOn(logger, 'error')
      .mockImplementation(() => undefined);

    await expect(service.load()).rejects.toThrow(err);
    expect(loggerErrorSpy).toHaveBeenCalledWith(
      '[DynamicConfigServer] failed to load config',
      err,
    );
  });
});
