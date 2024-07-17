package com.review.reviewms.Service;

import com.review.reviewms.Model.Review;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService
{

    ResponseEntity<List<Review>> getAllReviews(Long companyId);

    ResponseEntity<String> addReviews(Long companyId,Review review);

    ResponseEntity<Review> getReviewById(Long reviewId);

    ResponseEntity<String> updateReviewById(Long reviewId, Review updatedReview);

    ResponseEntity<String> deleteReviewById( Long reviewId);
}
