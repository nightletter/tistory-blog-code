package me.nightletter.playground;

import me.nightletter.playground.event.ReservationConfirmEvent;
import me.nightletter.playground.event.ReservationCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
class PlaygroundApplicationTests {

    @Autowired
    ApplicationEventPublisher publisher;

    @Test
    void contextLoads() {
        publisher.publishEvent(new ReservationCreatedEvent());
        publisher.publishEvent(new ReservationConfirmEvent());
    }

}
