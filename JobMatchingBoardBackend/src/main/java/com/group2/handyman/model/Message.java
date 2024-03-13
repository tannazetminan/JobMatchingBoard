package com.group2.handyman.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    private String content;
    private LocalDateTime timestamp;

	public Message() {}

	public Message(User user, Worker worker, String content, LocalDateTime timestamp) {
		this.user = user;
		this.worker = worker;
		this.content = content;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User sender) {
		this.user = sender;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker receiver) {
		this.worker = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}

