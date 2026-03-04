package me.nightletter.appapi.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nightletter.appapi.client.CarSpecClient;
import me.nightletter.appapi.domain.CarProcessor;
import me.nightletter.appapi.domain.CarSpec;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarRegistrationSubscriber {

    private final ObjectMapper objectMapper;
    private final CarSpecClient carSpecClient;
    private final CarProcessor carProcessor;
    private final RedisTemplate<String, Object> redisTemplate;

    public void receiveMessage(String messageJson) throws JsonProcessingException {
        CarRegistrationMessage message = objectMapper.readValue(messageJson, CarRegistrationMessage.class);

        log.info("taskId = {}", message.getTaskId());

        CarSpec carSpec = carSpecClient.getSpec(message.getOwner(), message.getLicensePlate());
        carProcessor.save(message.getOwner(), carSpec);

        redisTemplate.opsForValue().set(RedisKeys.TASK.toKey(message.getTaskId()), "SUCCESS");
    }
}
