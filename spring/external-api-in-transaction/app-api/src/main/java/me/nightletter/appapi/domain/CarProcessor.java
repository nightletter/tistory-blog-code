package me.nightletter.appapi.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CarProcessor {

    private final CarRepository carRepository;
    private final CarRegistrationTaskRepository carRegistrationTaskRepository;

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

    @Transactional
    public void save(
            String taskId,
            String owner,
            CarSpec carSpec
    ) {
        Car car = new Car(
                owner,
                carSpec.carVender(),
                carSpec.carName(),
                carSpec.subModel()
        );

        carRepository.save(car);
        carRegistrationTaskRepository.findById(taskId)
                .ifPresent(item -> {
                    item.updateStatus("SUCCESS");
                    carRegistrationTaskRepository.save(item);
                });
    }
}
