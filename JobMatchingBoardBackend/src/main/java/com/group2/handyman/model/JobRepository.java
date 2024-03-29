package com.group2.handyman.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByWorkerIdAndIsCompletedTrue(Long workerId);
    List<Job> findByClientId(Long clientId);
    List<Job> findByWorkerId(Long workerId);
    
    @Query("SELECT j FROM Job j JOIN j.skills s WHERE s.name = :skill")
    List<Job> findJobsBySkillName(@Param("skill") String skill);
}
