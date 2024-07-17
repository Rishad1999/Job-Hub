package com.jobms.job.Service;

import com.jobms.job.Model.Job;
import com.jobms.job.dto.JobDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface JobService {

    ResponseEntity<List<JobDTO>> findAllJobs();

    ResponseEntity<String> createJob(Job job);

    ResponseEntity<JobDTO> findJobById(Long id);

    ResponseEntity<String> deleteJobById(Long id);

    ResponseEntity<String> updatebyId(Long id, Job updatedjob);
}
