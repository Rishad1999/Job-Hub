package com.jobms.job.Service.impl;

import com.jobms.job.Model.Job;
import com.jobms.job.Repository.JobRepository;
import com.jobms.job.Service.JobService;
import com.jobms.job.clients.CompanyClient;
import com.jobms.job.clients.ReviewClient;
import com.jobms.job.dto.JobDTO;
import com.jobms.job.externalService.Company;
import com.jobms.job.externalService.Review;
import com.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService
{
    @Autowired
    JobRepository jobRepository;
    //public List<Job> jobs = new ArrayList<>();

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

    int attempt;

    @Override
//    @CircuitBreaker(name = "companyBreaker",
//            fallbackMethod = "companyBreakerFallback")
//    @Retry(name = "companyBreaker",
//            fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name = "companyBreaker",
            fallbackMethod = "companyBreakerFallback")
    public ResponseEntity<List<JobDTO>> findAllJobs()
    {
        System.out.println("Attempt" + ++attempt);
        List<Job> jobs = jobRepository.findAll();
        return new ResponseEntity<>(jobs.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.OK);
    }
    public ResponseEntity<List<String>> companyBreakerFallback(Exception e)
    {
        List<String> list = new ArrayList<>();
        list.add("dummy");
        return new ResponseEntity<>(list, HttpStatus.SERVICE_UNAVAILABLE);
    }

//    public List<String> companyBreakerFallback(Exception e)
//    {
//        List<String> list = new ArrayList<>();
//        list.add("dummy");
//        return list;
//    }

    private JobDTO convertToDto(Job job)
    {
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDTO(job,company,reviews);
        return jobDTO;
    }

//    @Override
//    public ResponseEntity<List<JobWithCompanyDTO>> findAllJobs()
//    {
//        List<Job> jobs = jobRepository.findAll();
//        List<JobWithCompanyDTO> jobWithCompanyDTOs = new ArrayList<>();
//
//        RestTemplate restTemplate = new RestTemplate();
//        for (Job job:jobs)
//        {
//            JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
//            jobWithCompanyDTO.setJob(job);
//            Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), Company.class);
//            jobWithCompanyDTO.setCompany(company);
//            jobWithCompanyDTOs.add(jobWithCompanyDTO);
//        }
//        return new ResponseEntity<>(jobWithCompanyDTOs,HttpStatus.OK);
//    }


    @Override
    public ResponseEntity<String> createJob(Job job) {
        jobRepository.save(job);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<JobDTO> findJobById(Long id) {
        if(jobRepository.existsById(id))
        {
            Job job = jobRepository.findById(id).orElse(null);
//            JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
//            jobWithCompanyDTO.setJob(job);
//            Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);
//            jobWithCompanyDTO.setCompany(company);

            return new ResponseEntity<>(convertToDto(job),HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> deleteJobById(Long id)
    {
//        for(Job job : jobs)
//        {
//            if(job.getId().equals(id))
//            {
//                jobs.remove(job);
//                return new ResponseEntity<>("Deleted", HttpStatus.OK);
//            }
//        }
        if(jobRepository.existsById(id))
        {
            jobRepository.deleteById(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("Deleted", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> updatebyId(Long id,Job updatedjob) {

        Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isPresent())
        {
            Job job = jobOptional.get();
            job.setDescription(updatedjob.getDescription());
            job.setTitle(updatedjob.getTitle());
            job.setMinSalary(updatedjob.getMinSalary());
            job.setMaxSalary(updatedjob.getMaxSalary());
            job.setLocation(updatedjob.getLocation());
            jobRepository.save(job);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed",HttpStatus.NOT_FOUND);

//        Iterator<Job> iterator=jobs.iterator();
//        while(iterator.hasNext())
//        {
//            Job job = iterator.next();
//            if(job.getId().equals(id))
//            {
//                job.setDescription(updatedjob.getDescription());
//                job.setTitle(updatedjob.getTitle());
//                job.setMinSalary(updatedjob.getMinSalary());
//                job.setMaxSalary(updatedjob.getMaxSalary());
//                job.setLocation(updatedjob.getLocation());
//
//                return new ResponseEntity<>("Success",HttpStatus.CREATED);
//            }
//        }
//        return new ResponseEntity<>("Failed",HttpStatus.NOT_FOUND);
    }
}
