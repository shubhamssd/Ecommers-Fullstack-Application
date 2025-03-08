package com.eCommers.eCommersApp.service;

import com.eCommers.eCommersApp.exception.ReviewException;
import com.eCommers.eCommersApp.model.Product;
import com.eCommers.eCommersApp.model.Review;
import com.eCommers.eCommersApp.model.User;
import com.eCommers.eCommersApp.repo.ProductRepository;
import com.eCommers.eCommersApp.repo.ReviewRepository;
import com.eCommers.eCommersApp.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;


    public Review addReviewToProduct(Integer productId, Integer userId, Review review) throws ReviewException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ReviewException("Product Not Found"));

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ReviewException("User Not Found In Database"));

        existingUser.getReviews().add(review);
        review.setUser(existingUser);
        existingProduct.getReviews().add(review);
        review.setProduct(existingProduct);
        userRepository.save(existingUser);
        productRepository.save(existingProduct);

        return reviewRepository.save(review);
    }


    public Review updateReviewToProduct(Integer reviewId, Review review) throws ReviewException {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException("Review With Id "+reviewId+"Not Found In DataBase"));

        existingReview.setComment(review.getComment());
        existingReview.setRating(review.getRating());
        return existingReview;
    }

    public void deleteReview(Integer reviewId) throws ReviewException {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException("Review With Id "+reviewId+"Not Found In DataBase"));

        reviewRepository.delete(existingReview);

    }


    public List<Review> getAllReviewOfProduct(Integer productId) throws ReviewException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ReviewException("Invalid Product id"));

        List<Review> allReviewsByProductId = reviewRepository.findAllReviewsByProductId(productId);
        if(allReviewsByProductId.isEmpty()) {
            throw new ReviewException ("No Rewiew Of Given Product is Available");
        }
        return allReviewsByProductId;
    }
}
