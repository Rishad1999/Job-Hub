package com.jobms.job.clients;

import com.jobms.job.externalService.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE",url = "${review-service.url}")
public interface ReviewClient
{
    @GetMapping("/reviews")
    List<Review> getReviews(@RequestParam("companyId") Long companyId);

}