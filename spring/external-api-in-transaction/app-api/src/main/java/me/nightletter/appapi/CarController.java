package me.nightletter.appapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/car")
    public ResponseEntity create() {
        carService.createCar(
                UUID.randomUUID().toString()
        );

        return ResponseEntity.ok()
                .build();
    }
}
