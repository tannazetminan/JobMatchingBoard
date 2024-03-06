package com.group2.handyman.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserAndWorker(User user, Worker worker);
    List<Message> findByWorkerAndUser(Worker worker, User user);
}


