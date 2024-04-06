package com.group2.handyman.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
	@JsonManagedReference
    private User client;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id")
	@JsonManagedReference
    private Worker worker;

    private boolean isCompleted;
    private Double rating;
    private String title;
	private String description;
    private double budget;
    
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Skill> skills = new HashSet<>();
    
   

	public Job() {}

	public Job(User client, Worker worker, boolean isCompleted, Double rating, String title, String description,
			   double budget, Set<Skill> skills ) {
		this.client = client;
		this.worker = worker;
		this.isCompleted = isCompleted;
		this.rating = rating;
		this.title = title;
		this.description = description;
		this.budget = budget;
		this.skills=skills;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String description) {
		this.title = description;
	}
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getClient() {
		return client;
	}
	public void setClient(User client) {
		this.client = client;
	}
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	public boolean isIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(boolean completed) {
		this.isCompleted = completed;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}

	 public Set<Skill> getSkills() {
			return skills;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean completed) {
		isCompleted = completed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSkills(Set<Skill> skills) {
			this.skills = skills;
		}
}
