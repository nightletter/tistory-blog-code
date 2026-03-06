package me.nightletter.appapi.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nightletter.appapi.messaging.RedisTopics;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static java.time.Duration.ofMinutes;
import static me.nightletter.appapi.messaging.RedisKeys.TASK;
import static me.nightletter.appapi.messaging.RedisTopics.CAR_REGISTRATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class CarEventListener {

    private final RedisTemplate<String, Object> redisTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onEvent(CarRegistrationEvent event) {
        log.info("CarEventListener.onEvent = {}", Thread.currentThread().getName());
        CarRegistrationTask task = event.task();
        redisTemplate.opsForValue().set(TASK.toKey(task.getId()), task.getStatus(), ofMinutes(10));
        redisTemplate.convertAndSend(CAR_REGISTRATION, task.getId());
    }
}
