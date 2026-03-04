package me.nightletter.appapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarRegistrationEvent {
    private final String taskId;
    private final String owner;
    private final String licensePlate;
}
