package me.nightletter.appapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final RestClient restClient;

    @Transactional
    public void createCar(String owner) {
        CarSpec carSpec = restClient.get()
                .uri("http://localhost:9090/car/specs")
                .retrieve()
                .body(CarSpec.class);

        carRepository.save(
                new Car(
                        owner,
                        carSpec.carVender(),
                        carSpec.carName(),
                        carSpec.subModel()
                )
        );
    }
}
