package me.nightletter.playground.event;

import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.stereotype.Component;

@Component
public class NotificationClientImpl implements NotificationClient {

    @Override
    public void notify(SlackNotifiable notifiable) {
        System.out.println(notifiable.getPayload().getPayload().getText());
    }
}
