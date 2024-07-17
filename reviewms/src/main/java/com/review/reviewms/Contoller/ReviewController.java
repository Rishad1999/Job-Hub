package com.review.reviewms.Contoller;

import com.review.reviewms.Model.Review;
import com.review.reviewms.Service.ReviewService;
import com.review.reviewms.messaging.ReviewMessageProducer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController
{
    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewMessageProducer reviewMessageProducer;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId)
    {
        return reviewService.getAllReviews(companyId);
    }
    @PostMapping
    public ResponseEntity<String> addReviews(@RequestParam Long companyId,@RequestBody Review review)
    {
        return reviewService.addReviews(companyId, review);
    }
    @GetMapping("{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId)
    {
        return reviewService.getReviewById(reviewId);
    }
    @PutMapping("{reviewId}")
    public ResponseEntity<String> updateReviewById(@PathVariable Long reviewId,@RequestBody Review updatedReview)
    {
        return reviewService.updateReviewById(reviewId,updatedReview);
    }
    @DeleteMapping("{reviewId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable Long reviewId)
    {
        return reviewService.deleteReviewById(reviewId);
    }
    @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId)
    {
        ResponseEntity<List<Review>> reviewList = reviewService.getAllReviews(companyId);
        return reviewList.getBody().stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }

}
