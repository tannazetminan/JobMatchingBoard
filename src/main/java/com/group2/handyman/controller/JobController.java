package com.group2.handyman.controller;

import java.util.List;

import com.group2.handyman.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobService jobService;


    // create a job
    @PostMapping("/create")
    public ResponseEntity<Job> createJob(@RequestParam Long clientId, @RequestParam Long workerId, @RequestBody Job jobDetails) {
        try {
            Job createdJob = jobService.createJob(clientId, workerId, jobDetails);
            return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    // get job base on skills
    @GetMapping("/skills/{skill}")
    public ResponseEntity<List<Job>> getJobBySkills(@PathVariable String skill){
    	
    	try {
    	    List<Job> jobs = jobRepository.findJobsBySkillName(skill);
    	    if (jobs.isEmpty()) {
    	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	    } else {
    	        return new ResponseEntity<>(jobs, HttpStatus.OK);
    	    }
    	} catch (Exception e) {
    	    return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
    	}

    }

    //  complete a job
    @PostMapping("/{jobId}/complete")
    public ResponseEntity<Job> completeJob(@PathVariable Long jobId) {
        try {
            Job completedJob = jobService.completeJob(jobId);
            return new ResponseEntity<>(completedJob, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    
    // give a new rating to a job, when it is finished
    @PostMapping("/{jobId}/rating")
    public ResponseEntity<Job> rateWorker(@PathVariable Long jobId, @RequestBody Rating rating) {
        try {
            Job updatedJob = jobService.rateJob(jobId, rating.getRating());
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
}

