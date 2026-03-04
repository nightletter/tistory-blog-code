package me.nightletter.appapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@RequiredArgsConstructor
public class CarProcessor {

    private final CarRepository carRepository;

    @Transactional
    public Long save(
            String owner,
            CarSpec carSpec
    ) {
        Car car = new Car(
                owner,
                carSpec.carVender(),
                carSpec.carName(),
                carSpec.subModel()
        );

        Car savedCar = carRepository.save(car);
        return savedCar.getId();
    }
}
