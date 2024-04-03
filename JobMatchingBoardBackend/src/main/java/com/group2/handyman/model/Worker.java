package com.group2.handyman.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "workers")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Worker{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String description;
    private String location;
    private double averageRating;
    private int totalRatings;
    private String workingHours;
	private double credit;
	private String phone;
	public enum PreferredCommunication {
		EMAIL, PHONE
	}

	@Column(name = "preferred_communication")
	@Enumerated(EnumType.STRING)
	private PreferredCommunication preferredCommunication;

	@ElementCollection
	private List<String> previousTransactions = new ArrayList<>();
	@JsonIgnoreProperties("client")
	@OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
	private Set<Job> jobs = new HashSet<>();
    
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Skill> skills = new HashSet<>();

    // Update the average rating
    public void addRating(double newRating) {
        this.averageRating = ((this.averageRating * this.totalRatings) + newRating) / (this.totalRatings + 1);
        this.totalRatings += 1;
    }
    
    public Worker() {}


	public Worker(String username, String password, String email, String description, String location,
				  double averageRating, int totalRatings, Set<Skill> skills, String workingHours,
				  double credit, String phone, PreferredCommunication preferredCommunication) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.description = description;
		this.location = location;
		this.averageRating = averageRating;
		this.totalRatings = totalRatings;
		this.skills = skills;
		this.workingHours = workingHours;
		this.credit = credit;
		this.phone = phone;
		this.preferredCommunication = preferredCommunication;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public int getTotalRatings() {
		return totalRatings;
	}

	public void setTotalRatings(int totalRatings) {
		this.totalRatings = totalRatings;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public Set<Job> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public PreferredCommunication getPreferredCommunication() {
		return preferredCommunication;
	}

	public void setPreferredCommunication(PreferredCommunication preferredCommunication) {
		this.preferredCommunication = preferredCommunication;
	}

	public List<String> getPreviousTransactions() {
		return previousTransactions;
	}

	public void setPreviousTransactions(List<String> previousTransactions) {
		this.previousTransactions = previousTransactions;
	}
}
