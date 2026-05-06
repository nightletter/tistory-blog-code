package me.nightletter.configserver;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RefreshHookListener {

    static final String REFRESH_STREAM = "config-service:refresh-events";
    static final Duration EVENT_TTL = Duration.ofMinutes(3);

    private static final Logger log = LoggerFactory.getLogger(RefreshHookListener.class);

    private final StringRedisTemplate redisTemplate;

    public RefreshHookListener(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @EventListener
    public void hook(RefreshScopeRefreshedEvent event) {
        Assert.notNull(event, "event must not be null");

        Instant now = Instant.now();
        Map<String, String> payload = Map.of(
                "event", "refresh",
                "scope", event.getName(),
                "timestamp", now.toString(),
                "expiresAt", now.plus(EVENT_TTL).toString()
        );

        RecordId recordId = redisTemplate.opsForStream().add(MapRecord.create(REFRESH_STREAM, payload));
        log.info("Published refresh event to Redis Streams [{}] with id {}", REFRESH_STREAM, recordId);
    }
}
