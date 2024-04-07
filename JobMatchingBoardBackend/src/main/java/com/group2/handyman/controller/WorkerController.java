package com.group2.handyman.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.group2.handyman.configuration.SecurityConfig;
import com.group2.handyman.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/workers")
public class WorkerController {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private SkillRepository skillRepository;
    
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
    public ResponseEntity<Worker> createWorker(@RequestBody WorkerCreateDto workerDto) {
        Worker worker = new Worker();
        worker.setUsername(workerDto.getUsername());
        worker.setPassword(workerDto.getPassword());
        worker.setEmail(workerDto.getEmail());
        worker.setDescription(workerDto.getDescription());
        worker.setLocation(workerDto.getLocation());

        Worker savedWorker = workerRepository.save(worker);

        // Create and save skills
        for (String skillName : workerDto.getSkillNames()) {
            Skill skill = new Skill(skillName, savedWorker, null);
            Skill savedSkill = skillRepository.save(skill);
            savedWorker.getSkills().add(savedSkill);
        }


        workerRepository.save(savedWorker);
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
    public ResponseEntity<Worker> updateWorker(@PathVariable Long id, @RequestBody WorkerUpdateDto workerDetailsDto) {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new RuntimeException("Worker not found with id " + id));

        worker.setUsername(workerDetailsDto.getUsername());
        worker.setEmail(workerDetailsDto.getEmail());
        worker.setDescription(workerDetailsDto.getDescription());
        worker.setLocation(workerDetailsDto.getLocation());

        final Worker updatedWorker = workerRepository.save(worker);
        return ResponseEntity.ok(updatedWorker);
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Worker> updateWorker(@PathVariable Long id, @RequestBody Worker workerDetails) {
//        Worker worker = workerRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Worker not found with id " + id));
//
//        // update worker
//        worker.setUsername(workerDetails.getUsername());
//        worker.setEmail(workerDetails.getEmail());
//        worker.setDescription(workerDetails.getDescription());
//        worker.setLocation(workerDetails.getLocation());
//
//        final Worker updatedWorker = workerRepository.save(worker);
//        return ResponseEntity.ok(updatedWorker);
//    }

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

    // get a worker's contact info based on their preferred communication
    @GetMapping("/{id}/contact")
    public ResponseEntity<String> getWorkerContactInfo(@PathVariable Long id) {
        return workerRepository.findById(id)
                .map(worker -> {
                    String contactInfo;
                    if (worker.getPreferredCommunication() == Worker.PreferredCommunication.EMAIL) {
                        contactInfo = "Email: " + worker.getEmail();
                    } else {
                        contactInfo = "Phone: " + worker.getPhone();
                    }
                    return new ResponseEntity<>(contactInfo, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>("Worker not found with id " + id, HttpStatus.NOT_FOUND));
    }

    // get a worker's transaction history
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<String>> getWorkerTransactions(@PathVariable Long id) {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new RuntimeException("Worker not found with id " + id));

        List<String> transactions = worker.getPreviousTransactions();
        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // get a worker's working hours
    @GetMapping("/{id}/working-hours")
    public ResponseEntity<String> getWorkerWorkingHours(@PathVariable Long id) {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new RuntimeException("Worker not found with id " + id));

        String workingHours = worker.getWorkingHours();
        if (workingHours == null || workingHours.isEmpty()) {
            return new ResponseEntity<>("No working hours set for this worker.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workingHours, HttpStatus.OK);
    }
}
