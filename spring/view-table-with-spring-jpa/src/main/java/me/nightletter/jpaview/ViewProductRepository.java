package me.nightletter.jpaview;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;

public interface ViewProductRepository extends JpaRepository<ViewProduct, ViewProductId> {
//    @Query("""
//            select v from ViewProduct v
//            where v.carInfoId = :carinfoId
//            and v.categoryId = :categoryId
//            and v.productId = :productId
//            """)
//    List<ViewProduct> getChildProductById(
//            Long carInfoId,
//            Long categoryId,
//            Long productId
//    );

    @Query("""
            select v from ViewProduct v
            where v.id.carInfoId = :carInfoId
                        and v.id.categoryId= 124
            """)

    @QueryHints({@QueryHint(name = "jakarta.persistence.cache.storeMode", value = "REFRESH")})
    List<ViewProduct> getByCarInfoId(Long carInfoId);
}
