package me.nightletter.jpaview;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@IdClass(ViewProductId.class)
public class ViewProduct {

    enum ProductGrade {
        STANDARD, PREMIUM
    }

//    @Id
//    private Long carInfoId;
//    @Id
//    private Long categoryId;
//    @Id
//    private Long productId;

    @EmbeddedId
    private ViewProductId id;

    private Long parentCategoryId;
    private Long childProductId;
    private Double serviceCharge;
    private String productName;
    private String categoryName;
    private Long productOptionVariantsId;
    @Enumerated(EnumType.STRING)
    private ProductGrade productGrade;
    private Boolean categoryActivated;
    private Integer price;
    private String partsNo;
}
