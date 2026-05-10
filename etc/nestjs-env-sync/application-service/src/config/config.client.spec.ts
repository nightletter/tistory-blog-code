import axios from 'axios';
import { ConfigServiceProvider } from './config-service.provider';
import { ConfigClient } from './config.client';

jest.mock('axios');

describe('ConfigClient', () => {
  let configServiceProvider: jest.Mocked<
    Pick<ConfigServiceProvider, 'requireEnv'>
  >;
  let client: ConfigClient;
  const mockedAxios = axios as jest.Mocked<typeof axios>;

  beforeEach(() => {
    configServiceProvider = {
      requireEnv: jest.fn((key: string) => {
        if (key === 'CONFIG_SERVER_URL') return 'http://config-server:8888';
        if (key === 'APPLICATION_NAME') return 'application-service';
        if (key === 'ENV') return 'dev';
        return '';
      }),
    };
    client = new ConfigClient(
      configServiceProvider as unknown as ConfigServiceProvider,
    );
    mockedAxios.get.mockReset();
  });

  it('환경변수 조합으로 Config Server URL을 만들어 요청한다', async () => {
    mockedAxios.get.mockResolvedValue({
      data: {
        propertySources: [],
      },
    });

    await client.fetchEnv();

    expect(configServiceProvider.requireEnv.mock.calls).toEqual([
      ['CONFIG_SERVER_URL'],
      ['APPLICATION_NAME'],
      ['ENV'],
    ]);
    expect(mockedAxios.get.mock.calls).toEqual([
      ['http://config-server:8888/application-service/dev'],
    ]);
  });

  it('요청 성공 시 응답 데이터 본문을 그대로 반환한다', async () => {
    const responseData = {
      propertySources: [
        {
          source: { 'application.greeting.message': 'hello' },
        },
      ],
    };
    mockedAxios.get.mockResolvedValue({ data: responseData });

    const result = await client.fetchEnv();

    expect(result).toEqual(responseData);
  });
});
