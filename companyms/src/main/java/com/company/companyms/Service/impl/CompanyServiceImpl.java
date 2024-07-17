package com.company.companyms.Service.impl;

import com.company.companyms.Model.Company;
import com.company.companyms.Repository.CompanyRepository;
import com.company.companyms.Service.CompanyService;
import com.company.companyms.clients.ReviewClient;
import com.company.companyms.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService
{
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReviewClient reviewClient;

    @Override
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> createCompany(Company company) {
        companyRepository.save(company);
        return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Company> getByIdCompany(Long id) {
        if(companyRepository.existsById(id))
        {
            return new ResponseEntity<>(companyRepository.findById(id).get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage)
    {
        System.out.println(reviewMessage.getDescription());
        Company company = companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(()-> new NotFoundException("Company not found" + reviewMessage.getCompanyId()));
        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);

    }

    @Override
    public ResponseEntity<String> deleteCompany(Long id) {
        if(companyRepository.existsById(id))
        {
            companyRepository.deleteById(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }




    @Override
    public ResponseEntity<String> updateCompany(Long id, Company updatedCompany) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent())
        {
            Company company = companyOptional.get();
            company.setName(updatedCompany.getName());
            company.setDescription(updatedCompany.getDescription());
            companyRepository.save(company);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed",HttpStatus.NOT_FOUND);
    }


}
