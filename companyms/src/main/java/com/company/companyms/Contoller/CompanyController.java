package com.company.companyms.Contoller;

import com.company.companyms.Model.Company;
import com.company.companyms.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/companies")
public class CompanyController
{
    @Autowired
    private CompanyService companyService;
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies()
    {
        return companyService.getAllCompanies();
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company)
    {
        return companyService.createCompany(company);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id)
    {
        return companyService.deleteCompany(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<Company> getByIdCompany(@PathVariable Long id)
    {
        return companyService.getByIdCompany(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company updatedCompany)
    {
        return companyService.updateCompany(id, updatedCompany);
    }
}
