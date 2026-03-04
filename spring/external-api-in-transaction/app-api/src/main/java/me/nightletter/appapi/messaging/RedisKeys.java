package me.nightletter.appapi.messaging;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RedisKeys {
    TASK("task:");

    private final String prefix;

    public String toKey(String value) {
        return prefix + value;
    }
}
