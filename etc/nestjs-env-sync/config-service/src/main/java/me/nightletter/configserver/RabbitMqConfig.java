package me.nightletter.configserver;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${app.refresh.exchange}")
    private String exchange;

    @Bean
    public TopicExchange configExchange() {
        return new TopicExchange(exchange);
    }
}
