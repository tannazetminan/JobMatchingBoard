package com.group2.handyman.controller;

import com.group2.handyman.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public void updateWorkerRating(Long workerId) {
        List<Job> completedJobs = jobRepository.findByWorkerIdAndIsCompletedTrue(workerId);
        double sumRatings = completedJobs.stream()
                              .mapToDouble(Job::getRating)
                              .sum();
        double averageRating = sumRatings / completedJobs.size();

        Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found"));
        worker.setAverageRating(averageRating);
        workerRepository.save(worker);
    }

    public List<Message> getMessagesForWorker(Long workerId, Long userId) {
        Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return messageRepository.findByWorkerAndUser(worker, user);
    }
}
