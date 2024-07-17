package com.company.companyms.dto;

import lombok.Data;

@Data
public class ReviewMessage
{
    private Long id;
    private String title;
    private String description;
    private double rating;
    private Long companyId;
}
