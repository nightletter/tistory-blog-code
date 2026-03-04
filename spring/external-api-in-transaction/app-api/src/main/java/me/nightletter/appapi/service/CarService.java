package me.nightletter.appapi.service;

import lombok.RequiredArgsConstructor;
import me.nightletter.appapi.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CarService {

//    v1 dependencies
    private final RestClient restClient;
    private final CarRepository carRepository;

    //    v2 dependencies
    private final CarSpecClient carSpecClient;
    private final CarProcessor carProcessor;

    @Transactional
    public void createCarV1(String owner) {
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

    public void createCarV2(String owner) {
        CarSpec spec = carSpecClient.getSpec();
        carProcessor.save(owner, spec);
    }

    public void createCarV3(String owner) {

    }
}
