package me.nightletter.appapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nightletter.appapi.client.CarSpecClient;
import me.nightletter.appapi.domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static me.nightletter.appapi.messaging.RedisKeys.TASK;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    //    v1 dependencies
    private final RestClient restClient;
    private final CarRepository carRepository;

    @Value("${app.external-api.url}")
    private String externalApiUrl;

    //    v2 dependencies
    private final CarSpecClient carSpecClient;
    private final CarProcessor carProcessor;

    //    v3 dependencies
    private final WebClient carSpecWebClient;
    private final CarRegistrationTaskRepository carRegistrationTaskRepository;

    //    v4 dependencies
    private final RedisTemplate<String, Object> redisTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Transactional
    public void registrationV1(String owner, String licensePlate) {
        CarSpec carSpec = restClient.get()
                .uri(externalApiUrl + "/car/specs")
                .retrieve()
                .body(CarSpec.class);

        carRepository.save(
                new Car(
                        owner,
                        carSpec.carVender(),
                        carSpec.carName(),
                        carSpec.subModel()
                )
        );
    }

    public void registrationV2(String owner, String licensePlate) {
        CarSpec spec = carSpecClient.getSpec(owner, licensePlate);
        carProcessor.save(owner, spec);
    }

    public String registrationV3(String owner, String licensePlate) {
        String taskId = UUID.randomUUID().toString();

        CarRegistrationTask savedTask = carRegistrationTaskRepository.save(
                new CarRegistrationTask(taskId, owner, licensePlate)
        );

        carSpecWebClient.get()
                .uri("/car/specs")
                .retrieve()
                .bodyToMono(CarSpec.class)
                .flatMap(spec -> {
                    log.info(Thread.currentThread().getName());
                    carProcessor.save(taskId, owner, spec);
                    return Mono.empty();
                })
                .subscribe();

        return savedTask.getId();
    }

    public String getTaskStatusV3(String taskId) {
        CarRegistrationTask fetch = carRegistrationTaskRepository.findById(taskId)
                .orElseThrow();

        return fetch.getStatus();
    }

    @Transactional
    public String registrationV4(String owner, String licensePlate) {
        String taskId = UUID.randomUUID().toString();

        var task = carRegistrationTaskRepository.save(
                new CarRegistrationTask(taskId, owner, licensePlate)
        );

        applicationEventPublisher.publishEvent(
                new CarRegistrationEvent(task)
        );

        return taskId;
    }

    public String getTaskStatusV4(String taskId) {
        String status = (String) redisTemplate.opsForValue().get(TASK.toKey(taskId));

        if (status != null) {
            return status;
        }

        return carRegistrationTaskRepository.findById(taskId)
                .orElseThrow(IllegalArgumentException::new)
                .getStatus();
    }
}
