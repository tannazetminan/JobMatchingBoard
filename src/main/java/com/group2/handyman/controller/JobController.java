package com.group2.handyman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group2.handyman.model.Job;
import com.group2.handyman.model.JobRepository;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WorkerService workerService;
    
    
    //Fetch job base on skills
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
    
    

    @PostMapping("/{jobId}/rate")
    public Job rateWorker(@PathVariable Long jobId, @RequestParam double rating) {
        // Find the job by id and ensure it exists
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        // Check if the job is completed; only completed jobs can be rated
        if (!job.isCompleted()) {
            throw new RuntimeException("Job is not completed yet. Cannot rate.");
        }
        
        // Set the rating for the job
        job.setRating(rating);
        Job updatedJob = jobRepository.save(job); // Persist the updated job with its rating
        
        // Update the worker's average rating based on this new job rating
        workerService.updateWorkerRating(job.getWorker().getId());

        return updatedJob; // Return the updated job entity
    }
    
    
    
}

