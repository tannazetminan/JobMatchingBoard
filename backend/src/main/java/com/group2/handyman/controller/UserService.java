package com.group2.handyman.controller;

import java.util.List;

import com.group2.handyman.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private WorkerRepository workerRepository;

    // get all messages between a user and a worker
    public List<Message> getMessagesForUser(Long userId, Long workerId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new RuntimeException("worker not found"));
        return messageRepository.findByUserAndWorker(user, worker);
    }

    public User createUser(User user) {
        // encrypt the password before saving the user
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }
}
