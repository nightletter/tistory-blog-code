package me.nightletter.playground.controllers;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestRequest {

    @Min(value = 1900, message = "1900년 이상이어야 합니다.")
    private Integer birthYear;
}
