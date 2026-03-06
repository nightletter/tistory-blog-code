package me.nightletter.appapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record CarRegistrationEvent(
        CarRegistrationTask task
) {

}
