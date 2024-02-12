package com.group2.handyman.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Worker extends User {
    private String description;
    private String location;
    private double averageRating;
    private int totalRatings;
    
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Skill> skills = new HashSet<>();

    // Update the average rating
    public void addRating(double newRating) {
        this.averageRating = ((this.averageRating * this.totalRatings) + newRating) / (this.totalRatings + 1);
        this.totalRatings += 1;
    }
    
    public Worker() {}
    
    

	public Worker(String description, String location, double averageRating, int totalRatings, Set<Skill> skills) {
		super();
		this.description = description;
		this.location = location;
		this.averageRating = averageRating;
		this.totalRatings = totalRatings;
		this.skills = skills;
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
    
    
    
}
