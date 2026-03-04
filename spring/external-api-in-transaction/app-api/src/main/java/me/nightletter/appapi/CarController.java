package me.nightletter.appapi;

import lombok.RequiredArgsConstructor;
import me.nightletter.appapi.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/v1/car")
    public ResponseEntity v1() {
        carService.createCarV1(
                UUID.randomUUID().toString()
        );

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/v2/car")
    public ResponseEntity v2() {
        carService.createCarV2(
                UUID.randomUUID().toString()
        );

        return ResponseEntity.ok()
                .build();
    }

}
