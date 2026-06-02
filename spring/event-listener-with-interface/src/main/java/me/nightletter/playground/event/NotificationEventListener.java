package me.nightletter.playground.event;

import com.slack.api.Slack;
import lombok.RequiredArgsConstructor;
import me.nightletter.playground.slack.SlackMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationClient client;

    @EventListener
    public void handleSlackNotifiable(SlackNotifiable o) {
        client.notify(o);
    }
}
