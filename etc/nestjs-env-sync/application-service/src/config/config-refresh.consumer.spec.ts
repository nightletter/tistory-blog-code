import { DynamicConfigService } from './dynamic-config.service';
import { ConfigRefreshConsumer } from './config-refresh.consumer';

describe('ConfigRefreshConsumer', () => {
  let dynamicConfigService: jest.Mocked<Pick<DynamicConfigService, 'load'>>;
  let consumer: ConfigRefreshConsumer;

  beforeEach(() => {
    dynamicConfigService = {
      load: jest.fn(),
    };
    consumer = new ConfigRefreshConsumer(
      dynamicConfigService as unknown as DynamicConfigService,
    );
  });

  it('refresh 이벤트를 받으면 설정을 다시 로드한다', async () => {
    const logger = Reflect.get(consumer, 'logger') as {
      log: (message: string) => void;
    };
    const loggerLogSpy = jest
      .spyOn(logger, 'log')
      .mockImplementation(() => undefined);

    await consumer.handleRefreshEvent({
      scope: 'application',
      pattern: 'config.refresh',
      event: 'refresh',
    });

    expect(dynamicConfigService.load).toHaveBeenCalledTimes(1);
    expect(loggerLogSpy).toHaveBeenCalledWith(
      'Config refresh completed successfully',
    );
  });

  it('refresh가 아닌 이벤트를 받으면 아무 작업도 하지 않는다', async () => {
    await consumer.handleRefreshEvent({
      scope: 'application',
      pattern: 'config.refresh',
      event: 'updated',
    });

    expect(dynamicConfigService.load).not.toHaveBeenCalled();
  });
});
