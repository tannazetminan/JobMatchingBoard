package com.group2.handyman.controller;

import java.util.List;

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
    
    // GET endpoint to fetch all workers
    @GetMapping
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    // GET endpoint to fetch a single worker by ID, using /workers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorkerById(@PathVariable Long id) {
        return workerRepository.findById(id)
                .map(worker -> new ResponseEntity<>(worker, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST method to create a new worker, using /workers
    @PostMapping
    public ResponseEntity<Worker> createWorker(@RequestBody Worker worker) {
        // Before creating, you might want to check for duplicates or validate the worker details
        Worker savedWorker = workerRepository.save(worker);
        return new ResponseEntity<>(savedWorker, HttpStatus.CREATED);
    }

 // POST method to give a new rate, based on the worker ID
    @PostMapping("/{id}/rate")
    public Worker rateWorker(@PathVariable Long id, @RequestParam double rating) {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new RuntimeException("Worker not found"));
        worker.addRating(rating);
        workerRepository.save(worker);
        return worker;
    }
    
    // PUT endpoint to update an existing worker, using /workers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Worker> updateWorker(@PathVariable Long id, @RequestBody Worker workerDetails) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found with id " + id));
        
        // Update worker details. Here, we update some fields as examples. Adjust according to your worker entity
        worker.setUsername(workerDetails.getUsername());
        worker.setEmail(workerDetails.getEmail());
        worker.setDescription(workerDetails.getDescription());
        worker.setLocation(workerDetails.getLocation());
        // Add other fields that you need to update
        
        final Worker updatedWorker = workerRepository.save(worker);
        return ResponseEntity.ok(updatedWorker);
    }
}
