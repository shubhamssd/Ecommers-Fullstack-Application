package com.eCommers.eCommersApp.repo;

import com.eCommers.eCommersApp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.product.productId = :productId")
    List<Review> findAllReviewsByProductId(@Param("productId") Integer productId);

}
