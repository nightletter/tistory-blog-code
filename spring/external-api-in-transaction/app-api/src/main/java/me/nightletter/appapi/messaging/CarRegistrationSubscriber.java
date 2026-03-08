package me.nightletter.appapi.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nightletter.appapi.client.CarSpecClient;
import me.nightletter.appapi.domain.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarRegistrationSubscriber {

    private final WebClient carSpecWebClient;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CarProcessor carProcessor;

    public void receiveMessage(String taskId) {
        carSpecWebClient.get()
                .uri("/car/specs")
                .retrieve()
                .bodyToMono(CarSpec.class)
                .doOnNext(carSpec -> {
                    log.info("receiveMessage = {}", Thread.currentThread().getName());
                    carProcessor.confirmRegistration(taskId, carSpec);
                    redisTemplate.opsForValue().set(RedisKeys.TASK.toKey(taskId), "SUCCESS");
                })
                .doOnError(ex -> {
                    log.error("receiveMessage = {}" + taskId, ex);
                    redisTemplate.opsForValue().set(RedisKeys.TASK.toKey(taskId), "FAILED");
                })
                .subscribe();
    }
}
