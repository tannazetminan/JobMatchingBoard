package com.group2.handyman.controller;

import java.util.List;
import java.util.Set;

import com.group2.handyman.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group2.handyman.model.Worker;
import com.group2.handyman.model.WorkerRepository;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    @Autowired
    private WorkerRepository workerRepository;
    
    // get all workers
    @GetMapping
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    // get a single worker by ID
    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorkerById(@PathVariable Long id) {
        return workerRepository.findById(id)
                .map(worker -> new ResponseEntity<>(worker, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    // get worker base on skills
    @GetMapping("/skills/{skill}")
    public ResponseEntity<List<Worker>> getWorkersBySkill(@PathVariable String skill) {
        List<Worker> workers = workerRepository.findBySkillsName(skill);
        if (workers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(workers, HttpStatus.OK);
    }
    
    // get worker base on rating
    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Worker>> getWorkerByRating(@PathVariable int rating ){
    	
    	List <Worker> workers= workerRepository.findByRating(rating);
    		if (workers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(workers, HttpStatus.OK);
    }

    // create a new worker
    @PostMapping
    public ResponseEntity<Worker> createWorker(@RequestBody Worker worker) {
        Worker savedWorker = workerRepository.save(worker);
        return new ResponseEntity<>(savedWorker, HttpStatus.CREATED);
    }

 // give a new rate, based on the worker ID
    @PostMapping("/{id}/rate")
    public Worker rateWorker(@PathVariable Long id, @RequestParam double rating) {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new RuntimeException("Worker not found"));
        worker.addRating(rating);
        workerRepository.save(worker);
        return worker;
    }
    
    // update an existing worker
    @PutMapping("/{id}")
    public ResponseEntity<Worker> updateWorker(@PathVariable Long id, @RequestBody Worker workerDetails) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found with id " + id));
        
        // update worker
        worker.setUsername(workerDetails.getUsername());
        worker.setEmail(workerDetails.getEmail());
        worker.setDescription(workerDetails.getDescription());
        worker.setLocation(workerDetails.getLocation());
        
        final Worker updatedWorker = workerRepository.save(worker);
        return ResponseEntity.ok(updatedWorker);
    }

    // get a worker's past jobs
    @GetMapping("/{id}/jobs")
    public ResponseEntity<Set<Job>> getWorkerJobs(@PathVariable Long id) {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new RuntimeException("Worker not found with id " + id));

        Set<Job> jobs = worker.getJobs();
        if (jobs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
}
