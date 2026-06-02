package me.nightletter.playground.event;

import me.nightletter.playground.slack.SlackMessage;

public class ReservationCreatedEvent implements SlackNotifiable {

    @Override
    public SlackMessage getPayload() {
        return new SlackMessage(
                "예약 알림 채널",
                "신규 예약 알림"
        );
    }
}
