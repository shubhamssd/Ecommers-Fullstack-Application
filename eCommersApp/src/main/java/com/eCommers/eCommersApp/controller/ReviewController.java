package com.eCommers.eCommersApp.controller;

import com.eCommers.eCommersApp.model.Review;
import com.eCommers.eCommersApp.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecom/product-reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    @PostMapping("/{productId}/{userId}")
    public ResponseEntity<Review> addReviewToProduct(@PathVariable Integer productId, @PathVariable Integer userId,
                                                     @RequestBody Review review) {
        Review addedReview = reviewService.addReviewToProduct(productId, userId, review);
        return ResponseEntity.ok(addedReview);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReviewToProduct(@PathVariable Integer reviewId, @Valid @RequestBody Review review) {
        Review updatedReview = reviewService.updateReviewToProduct(reviewId, review);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<Review>> getAllReviewOfProduct(@PathVariable Integer productId) {
        List<Review> allReviews = reviewService.getAllReviewOfProduct(productId);
        return new ResponseEntity<>(allReviews, HttpStatus.OK);
    }
}