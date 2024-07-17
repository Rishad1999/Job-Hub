package com.jobms.job.dto;

import com.jobms.job.externalService.Company;
import com.jobms.job.externalService.Review;
import lombok.Data;

import java.util.List;

@Data
public class JobDTO
{
    private Long id;
    private String title;
    private String description;
    private Integer minSalary;
    private Integer maxSalary;
    private String location;

    private Company company;
    private List<Review> review;
}
