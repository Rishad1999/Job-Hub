package com.company.companyms.Service;

import com.company.companyms.Model.Company;
import com.company.companyms.dto.ReviewMessage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyService {
    ResponseEntity<List<Company>> getAllCompanies();

    ResponseEntity<String> updateCompany(Long id, Company updatedCompany);

    ResponseEntity<String> createCompany(Company company);

    ResponseEntity<String> deleteCompany(Long id);

    ResponseEntity<Company> getByIdCompany(Long id);

    public void updateCompanyRating(ReviewMessage reviewMessage);
}
