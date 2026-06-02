package me.nightletter.playground.slack;

import com.slack.api.webhook.Payload;
import lombok.Getter;

@Getter
public class SlackMessage {

    private String channel;
    private Payload payload;

    public SlackMessage(String channel, String message) {
        this.channel = channel;
        this.payload = Payload.builder()
                .text(message)
                .build();
    }
}
