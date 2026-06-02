package me.nightletter.playground.event;

import me.nightletter.playground.slack.SlackMessage;

public interface SlackNotifiable {
    SlackMessage getPayload();
}
