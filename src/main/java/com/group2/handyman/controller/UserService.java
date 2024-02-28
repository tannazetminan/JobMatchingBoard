package com.group2.handyman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.group2.handyman.model.Message;
import com.group2.handyman.model.MessageRepository;
import com.group2.handyman.model.User;
import com.group2.handyman.model.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesForUser(Long userId, Long otherUserId) {
        User user1 = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User user2 = userRepository.findById(otherUserId).orElseThrow(() -> new RuntimeException("Other user not found"));
        return messageRepository.findBySenderAndReceiver(user1, user2);
    }

    public User createUser(User user) {
        // Hash the password before saving the user
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }
}
