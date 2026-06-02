package me.nightletter.playground.event;

import lombok.Getter;
import me.nightletter.playground.slack.SlackMessage;

@Getter
public class SlackMessageEvent {
    private Long id;
    private String text;

    public SlackMessage getSlackMessagePayload() {
        return new SlackMessage(
                "channelUrl",
                "슬랙 메시지 내용입니다."
        );
    }
}
