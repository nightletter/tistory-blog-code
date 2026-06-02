package me.nightletter.playground.event;

import lombok.Getter;

@Getter
public class NotifyPayload {
    private String message;

    public NotifyPayload(String message) {
        this.message = message;
    }
}
