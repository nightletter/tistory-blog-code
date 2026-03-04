package me.nightletter.appapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CarSpecClient {

    private final RestClient restClient;

    public CarSpec getSpec() {
        CarSpec carSpec = restClient.get()
                .uri("http://localhost:9090/car/specs")
                .retrieve()
                .body(CarSpec.class);

        return carSpec;
    }
}
