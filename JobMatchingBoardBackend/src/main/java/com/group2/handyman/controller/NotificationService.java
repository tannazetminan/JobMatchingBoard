package com.group2.handyman.controller;

import com.group2.handyman.model.Job;
import com.group2.handyman.model.User;
import com.group2.handyman.model.Worker;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// for sending notifications to users and workers
@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendJobCreationNotification(User user, Job job) {
        logger.info("Notification to User {}: A new job '{}' has been created.", user.getUsername(), job.getTitle());
    }

    public void sendJobCompletionNotification(Worker worker, Job job) {
        logger.info("Notification to Worker {}: Job '{}' has been completed.", worker.getUsername(), job.getTitle());
    }
}