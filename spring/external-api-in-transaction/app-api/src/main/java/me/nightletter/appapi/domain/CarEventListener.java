package me.nightletter.appapi.domain;

import lombok.RequiredArgsConstructor;
import me.nightletter.appapi.messaging.CarRegistrationMessage;
import me.nightletter.appapi.messaging.RedisKeys;
import me.nightletter.appapi.messaging.RedisTopics;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;

@Component
@RequiredArgsConstructor
public class CarEventListener {

    private final RedisTemplate<String, Object> redisTemplate;

    @EventListener
    public void onEvent(CarRegistrationEvent event) {
        redisTemplate.opsForValue().set(RedisKeys.TASK.toKey(event.getTaskId()), "PENDING", ofMinutes(10));
        redisTemplate.convertAndSend(
                RedisTopics.CAR_REGISTRATION,
                new CarRegistrationMessage(event.getTaskId(), event.getOwner(), event.getLicensePlate())
        );
    }
}
