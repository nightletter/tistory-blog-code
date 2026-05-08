package me.nightletter.configserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;

@ExtendWith(MockitoExtension.class)
class RefreshHookListenerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private RefreshScopeRefreshedEvent event;

    private final String exchange = "exchange";
    private final String routingKey = "routingKey";

    @Test
    void publishesRefreshPayloadWithTimestampAndExpiry() throws Exception {
        when(event.getName()).thenReturn("config-service");
        var listener = new RefreshHookListener(rabbitTemplate, exchange, routingKey);

        listener.hook(event);

        ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        verify(rabbitTemplate).convertAndSend(eq(exchange), eq(routingKey), payloadCaptor.capture());

        @SuppressWarnings("unchecked")
        Map<String, Object> payload = objectMapper.readValue(payloadCaptor.getValue(), Map.class);
        assertThat(payload).containsEntry("event", "refresh");
        assertThat(payload).containsEntry("scope", "config-service");
        assertThat(payload).containsKey("timestamp");
        assertThat(payload).containsKey("expiresAt");

        Instant expiresAt = Instant.parse(payload.get("expiresAt").toString());
        assertThat(expiresAt).isAfter(Instant.now().plusSeconds(170));
        assertThat(expiresAt).isBefore(Instant.now().plusSeconds(190));
    }

    @Test
    void publishesToExchangeWhenConfigured() {
        when(event.getName()).thenReturn("config-service");
        RefreshHookListener listener = new RefreshHookListener(
                rabbitTemplate,
                "config.events.exchange",
                "config.refresh"
        );

        listener.hook(event);

        verify(rabbitTemplate).convertAndSend(eq("config.events.exchange"), eq("config.refresh"), anyString());
        verifyNoMoreInteractions(rabbitTemplate);
    }
}
