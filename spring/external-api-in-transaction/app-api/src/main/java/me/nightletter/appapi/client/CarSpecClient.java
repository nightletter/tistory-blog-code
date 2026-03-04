package me.nightletter.appapi.client;

import lombok.RequiredArgsConstructor;
import me.nightletter.appapi.domain.CarSpec;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CarSpecClient {

    private final RestClient restClient;

    public CarSpec getSpec(String owner, String licensePlate) {
        CarSpec carSpec = restClient.get()
                .uri("http://localhost:9090/car/specs")
                .retrieve()
                .body(CarSpec.class);

        return carSpec;
    }
}
