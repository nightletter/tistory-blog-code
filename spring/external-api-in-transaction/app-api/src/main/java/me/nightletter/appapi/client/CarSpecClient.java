package me.nightletter.appapi.client;

import lombok.RequiredArgsConstructor;
import me.nightletter.appapi.domain.CarSpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CarSpecClient {

    private final RestClient restClient;

    @Value("${app.external-api.url}")
    private String externalApiUrl;

    public CarSpec getSpec(String owner, String licensePlate) {
        CarSpec carSpec = restClient.get()
                .uri(externalApiUrl + "/car/specs")
                .retrieve()
                .body(CarSpec.class);

        return carSpec;
    }
}
