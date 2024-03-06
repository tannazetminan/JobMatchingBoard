package com.group2.handyman.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.group2.handyman.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public List<Message> getMessagesForUser(@PathVariable Long userId, @RequestParam Long workerId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found"));
        
        // get messages where user is the sender and worker is the receiver
        List<Message> messagesFromUserToWorker = messageRepository.findByUserAndWorker(user, worker);
        
        // get messages where worker is the sender and user is the receiver
//        List<Message> messagesFromUser2ToUser1 = messageRepository.findBySenderAndReceiver(worker, user);
        
        // Combine and return the messages
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(messagesFromUserToWorker);
//        allMessages.addAll(messagesFromUser2ToUser1);
        
        return allMessages;
    }


    @GetMapping("/worker/{workerId}")
    public List<Message> getMessagesForWorker(@PathVariable Long workerId, @RequestParam Long userId) {
        Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // get messages where worker is the sender and user is the receiver
        List<Message> messagesFromWorkerToUser = messageRepository.findByWorkerAndUser(worker, user);

        // Combine and return the messages
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(messagesFromWorkerToUser);

        return allMessages;
    }
}
