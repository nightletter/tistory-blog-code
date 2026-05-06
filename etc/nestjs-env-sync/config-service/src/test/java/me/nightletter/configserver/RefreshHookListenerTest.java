package me.nightletter.configserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

@ExtendWith(MockitoExtension.class)
class RefreshHookListenerTest {

    @Mock
    StringRedisTemplate redisTemplate;

    @Mock
    StreamOperations<String, Object, Object> streamOperations;

    @Mock
    RefreshScopeRefreshedEvent event;

    @InjectMocks
    RefreshHookListener listener;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForStream()).thenReturn(streamOperations);
        when(streamOperations.add(any(MapRecord.class))).thenReturn(RecordId.of("1-0"));
        when(event.getName()).thenReturn("config-service");
    }

    @Test
    void publishes_refresh_event_to_redis_stream() {
        listener.hook(event);

        ArgumentCaptor<MapRecord<String, Object, Object>> captor = ArgumentCaptor.forClass(MapRecord.class);
        verify(streamOperations).add(captor.capture());

        Map<Object, Object> payload = captor.getValue().getValue();
        assertThat(captor.getValue().getStream()).isEqualTo(RefreshHookListener.REFRESH_STREAM);
        assertThat(payload).containsEntry("event", "refresh");
        assertThat(payload).containsEntry("scope", "config-service");
        assertThat(payload).containsKey("timestamp");
        assertThat(payload).containsKey("expiresAt");

        Instant expiresAt = Instant.parse(payload.get("expiresAt").toString());
        assertThat(expiresAt).isAfter(Instant.now().plusSeconds(170));
        assertThat(expiresAt).isBefore(Instant.now().plusSeconds(190));
    }
}
