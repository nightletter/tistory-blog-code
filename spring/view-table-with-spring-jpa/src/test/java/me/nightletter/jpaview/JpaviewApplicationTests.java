package me.nightletter.jpaview;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class JpaviewApplicationTests {

    protected static Long car_info_id = 402L;

    @Autowired
    ViewProductReader sut;

    @Test
    void run() {
        List<ViewProduct> items = sut.getByCarInfoId(car_info_id);
        List<ViewProduct> filtered = items.stream()
                .filter(item -> item.getId().getCategoryId().equals(124L))
                .toList();

        System.out.println();
    }
}
