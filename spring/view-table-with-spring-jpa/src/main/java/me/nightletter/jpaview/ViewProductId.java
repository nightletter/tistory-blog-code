package me.nightletter.jpaview;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Embeddable
@Getter
public class ViewProductId implements Serializable {
    private Long carInfoId;
    private Long categoryId;
    private Long productId;
}
