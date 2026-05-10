package me.nightletter.configserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component
public class RefreshHookListener {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public RefreshHookListener(
            RabbitTemplate rabbitTemplate,
            @Value("${app.refresh.exchange}") String exchange,
            @Value("${app.refresh.routing-key}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @EventListener
    public void hook(RefreshScopeRefreshedEvent event) {
        Assert.notNull(event, "event must not be null");

        Map<String, Object> data = new HashMap<>();
        data.put("pattern", "config.refresh");
        data.put("scope", event.getName());
        data.put("event", "refresh");
        
        Map<String, Object> packet = new HashMap<>();
        packet.put("pattern", "config.refresh");
        packet.put("data", data);

        String body = serializePayload(packet);

        rabbitTemplate.convertAndSend(exchange, routingKey, body);
    }

    private String serializePayload(Map<String, Object> payload) {
        try {
            return OBJECT_MAPPER.writeValueAsString(payload);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize refresh payload", ex);
        }
    }
}
