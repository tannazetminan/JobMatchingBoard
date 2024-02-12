package com.group2.handyman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group2.handyman.model.Job;
import com.group2.handyman.model.JobRepository;
import com.group2.handyman.model.Worker;
import com.group2.handyman.model.WorkerRepository;

import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private JobRepository jobRepository;

    public void updateWorkerRating(Long workerId) {
        List<Job> completedJobs = jobRepository.findByWorkerIdAndCompletedTrue(workerId);
        double sumRatings = completedJobs.stream()
                              .mapToDouble(Job::getRating)
                              .sum();
        double averageRating = sumRatings / completedJobs.size();

        Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found"));
        worker.setAverageRating(averageRating);
        workerRepository.save(worker);
    }
}
