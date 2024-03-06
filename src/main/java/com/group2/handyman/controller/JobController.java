package com.group2.handyman.controller;

import java.util.List;

import com.group2.handyman.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group2.handyman.model.Job;
import com.group2.handyman.model.JobRepository;

// see the list of all jobs
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WorkerService workerService;
    
    
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
    
    
    // give a new rating to a job, when it is finished
    @PostMapping("/{jobId}/rating")
    public Job rateWorker(@PathVariable Long jobId, @RequestBody Rating rating) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        // only completed jobs can be rated
        if (!job.isIsCompleted()) {
            throw new RuntimeException("Job is not completed yet. Cannot rate.");
        }

        job.setRating(rating.getRating());
        Job updatedJob = jobRepository.save(job);
        
        // update the worker's average rating based on this new job rating
        workerService.updateWorkerRating(job.getWorker().getId());

        return updatedJob;
    }
    
    
    
}

