package me.nightletter.appapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String owner;
    private String vender;
    private String model;
    private String trim;

    public Car(String owner, String vender, String model, String trim) {
        this.owner = owner;
        this.vender = vender;
        this.model = model;
        this.trim = trim;
    }
}
