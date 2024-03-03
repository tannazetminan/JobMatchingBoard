package com.group2.handyman.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
	 @Query("SELECT w FROM Worker w JOIN w.skills s WHERE s.name = :skill")
	    List<Worker> findBySkillsName(@Param("skill") String skill);
	 
	 @Query("SELECT w FROM Worker w WHERE w.totalRatings = :rating")
	 List<Worker> findByRating(@Param("rating") int rating);
	
}
