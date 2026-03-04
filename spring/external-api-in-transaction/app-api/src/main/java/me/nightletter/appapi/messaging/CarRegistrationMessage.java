package me.nightletter.appapi.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarRegistrationMessage {
    private String taskId;
    private String owner;
    private String licensePlate;
}
