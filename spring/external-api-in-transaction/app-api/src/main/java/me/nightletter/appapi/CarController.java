package me.nightletter.appapi;

import lombok.RequiredArgsConstructor;
import me.nightletter.appapi.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    /**
     * api call in transaction
     * @return
     */
    @PostMapping("/v1/car")
    public ResponseEntity v1() {
        carService.registrationV1(
                UUID.randomUUID().toString(),
                "123가1234"
        );

        return ok()
                .build();
    }

    /**
     * api call out transaction
     * @return
     */
    @PostMapping("/v2/car")
    public ResponseEntity v2() {
        carService.registrationV2(
                UUID.randomUUID().toString(),
                "123가1234"
        );

        return ok()
                .build();
    }

    /**
     * flux webclient
     * @return
     */
    @PostMapping("/v3/car")
    public ResponseEntity v3() {
        String taskId = carService.registrationV3(
                UUID.randomUUID().toString(),
                "123가1234"
        );

        Map<String, String> result = new HashMap<>();
        result.put("taskId", taskId);

        return ok(result);
    }

    @GetMapping("/v3/car/{taskId}/status")
    public ResponseEntity v3Status(@PathVariable String taskId) {
        String status = carService.getCarStatusV3(taskId);
        Map<String, String> result = new HashMap<>();
        result.put("status", status);
        return ok(result);
    }

    /**
     * redis pub/sub
     * @return
     */
    @PostMapping("/v4/car")
    public ResponseEntity v4() {
        String taskId = carService.registrationV4(
                UUID.randomUUID().toString(),
                "123가1234"
        );

        Map<String, String> result = new HashMap<>();
        result.put("taskId", taskId);

        return ok(result);
    }

    @GetMapping("/v4/car/{taskId}/status")
    public ResponseEntity v4Status(@PathVariable String taskId) {
        String status = carService.getCarStatusV4(taskId);
        Map<String, String> result = new HashMap<>();
        result.put("status", status);

        return ok(result);
    }

}