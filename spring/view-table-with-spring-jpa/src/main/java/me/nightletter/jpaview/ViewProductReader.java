package me.nightletter.jpaview;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ViewProductReader {
    private final ViewProductRepository viewProductRepository;

    public List<ViewProduct> getByCarInfoId(Long carInfoId) {
        return viewProductRepository.getByCarInfoId(carInfoId);
    }
}
