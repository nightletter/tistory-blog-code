package me.nightletter.playground.domain;

import lombok.Getter;
import org.springframework.util.Assert;

import static org.springframework.util.Assert.*;

@Getter
public class Product {
    private Long id;
    private String name;
    private Integer price;

    public Product(Long id, String name, Integer price) {
//        hasText(name, "Name must not be empty");
//        notNull(price, "Price must not be null");
//        isTrue(price >= 0, "Price must not be negative");

        assert price > 0;

        this.id = id;
        this.name = name;
        this.price = price;
    }
}
