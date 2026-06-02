package me.nightletter.playground.event;

import me.nightletter.playground.slack.SlackMessage;

public class ReservationConfirmEvent implements SlackNotifiable{

    @Override
    public SlackMessage getPayload() {
        return new SlackMessage(
                "예약 알림 채널",
                "예약 확정 메시지"
        );
    }
}
