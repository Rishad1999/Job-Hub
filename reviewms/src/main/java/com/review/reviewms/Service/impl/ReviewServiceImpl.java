package com.review.reviewms.Service.impl;

import com.review.reviewms.Model.Review;
import com.review.reviewms.Repository.ReviewRepository;
import com.review.reviewms.Service.ReviewService;
import com.review.reviewms.messaging.ReviewMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService
{
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewMessageProducer reviewMessageProducer;

    @Override
    public ResponseEntity<List<Review>> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addReviews(Long companyId,Review review) {
        if(companyId!=null&&review!=null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("review added",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("no company available",HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Review> getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review!=null)
        {
            return new ResponseEntity<>(review,HttpStatus.OK);
        }

        //reviews.stream().filter(review->review.getId().equals(reviewId)).findFirst().orElse(null);

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> updateReviewById(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(reviewId!=null)
        {
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(review);
            return new ResponseEntity<>("Review Updated",HttpStatus.OK);
        }
        return new ResponseEntity<>("Review Not Found",HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> deleteReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review!=null)
        {
            reviewRepository.delete(review);
            return new ResponseEntity<>("Review Deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Review Not Deleted",HttpStatus.NOT_FOUND);
    }
}
