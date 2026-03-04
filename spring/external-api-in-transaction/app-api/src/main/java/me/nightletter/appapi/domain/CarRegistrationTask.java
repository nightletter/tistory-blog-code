package me.nightletter.appapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarRegistrationTask {

    @Id
    private String id;
    private String owner;
    private String status;

    public CarRegistrationTask(String id, String owner) {
        this.id = id;
        this.owner = owner;
        this.status = "PENDING";
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}
