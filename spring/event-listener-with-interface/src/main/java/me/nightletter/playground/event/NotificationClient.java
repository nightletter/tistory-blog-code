package me.nightletter.playground.event;

public interface NotificationClient {
    void notify(SlackNotifiable notifiable);
}
