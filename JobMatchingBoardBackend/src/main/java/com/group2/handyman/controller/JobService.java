package com.group2.handyman.controller;

import com.group2.handyman.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Job createJob(Long clientId,  Job jobDetails) {
        User client = userRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found."));
        //Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found."));

        // if client has enough credit
        if (client.getCredit() < jobDetails.getBudget()) {
            throw new RuntimeException("Client does not have enough credit.");
        }

        // deduct job budget from client's credit
        client.setCredit(client.getCredit() - jobDetails.getBudget());
        userRepository.save(client);

        // set client and worker for the job
        jobDetails.setClient(client);
        //jobDetails.setWorker(worker);

        // add job to worker's job list
        //worker.getJobs().add(jobDetails);

        // save the job
        Job savedJob = jobRepository.save(jobDetails);
        //workerRepository.save(worker);

        // send job creation notification
        notificationService.sendJobCreationNotification(client, savedJob);

        return savedJob;
    }

    @Transactional
    public Job updateJob(Long jobId, JobUpdateDto jobUpdateDto) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found."));

        if (jobUpdateDto.getCompleted() != null) job.setIsCompleted(jobUpdateDto.getCompleted());
        if (jobUpdateDto.getRating() != null) job.setRating(jobUpdateDto.getRating());
        if (jobUpdateDto.getTitle() != null) job.setTitle(jobUpdateDto.getTitle());
        if (jobUpdateDto.getDescription() != null) job.setDescription(jobUpdateDto.getDescription());
        if (jobUpdateDto.getBudget() != null) job.setBudget(jobUpdateDto.getBudget());
        if (jobUpdateDto.getWorkerId() != null) {
            Worker worker = workerRepository.findById(jobUpdateDto.getWorkerId())
                    .orElseThrow(() -> new RuntimeException("Worker not found."));
            job.setWorker(worker);
            worker.getJobs().add(job);
        }

        return jobRepository.save(job);
    }

    @Transactional
    public Job completeJob(Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found."));

        if (job.isIsCompleted()) {
            throw new RuntimeException("Job is already completed.");
        }

        // set job as completed
        job.setIsCompleted(true);
        Job updatedJob = jobRepository.save(job);

        // snd job completion notification
        notificationService.sendJobCompletionNotification(job.getWorker(), job);

        // add job budget to worker's credit
        Worker worker = job.getWorker();
        worker.setCredit(worker.getCredit() + job.getBudget());

        // add transaction history to worker
        String transaction = "Completed job '" + job.getTitle() + "' for $" + job.getBudget();
        worker.getPreviousTransactions().add(transaction);

        workerRepository.save(worker);

        return updatedJob;
    }

    public Job rateJob(Long jobId, double rating) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

//        if (!job.isIsCompleted()) {
//            throw new RuntimeException("Job is not completed yet. Cannot rate.");
//        }

        // update job rating
        job.setRating(rating);
        Job updatedJob = jobRepository.save(job);

        // update worker's rating with the new job rating
        workerService.updateWorkerRating(job.getWorker().getId(), rating);

        return updatedJob;
    }
}