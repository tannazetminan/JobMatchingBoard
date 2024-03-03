package com.group2.handyman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.group2.handyman.model.Job;
import com.group2.handyman.model.JobRepository;
import com.group2.handyman.model.Skill;
import com.group2.handyman.model.SkillRepository;
import com.group2.handyman.model.User;
import com.group2.handyman.model.UserRepository;
import com.group2.handyman.model.Worker;
import com.group2.handyman.model.WorkerRepository;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class HandymanApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandymanApplication.class, args);
    }

    @Bean
    public ApplicationRunner init(UserRepository userRepository, WorkerRepository workerRepository,
                                  JobRepository jobRepository, SkillRepository skillRepository,
                                  PasswordEncoder passwordEncoder) {
        return args -> {
            // Create and save Users
            List<User> users = IntStream.rangeClosed(1, 5)
                    .mapToObj(i -> new User("user" + i, passwordEncoder.encode("pass" + i), "user" + i + "@example.com"))
                    .map(userRepository::save)
                    .collect(Collectors.toList());

            // Create and save Workers along with their Skills
            List<Worker> workers = IntStream.rangeClosed(1, 5)
                    .mapToObj(i -> new Worker("worker" + i, passwordEncoder.encode("worker" + i), "worker" + i + "@example.com", "Description" + i, "Location" + i, 5.0 - i % 5, i, new HashSet<>(), "2024-01-01"))
                    .map(workerRepository::save) // Save to generate ID
                    .collect(Collectors.toList());

            
            IntStream.range(0, users.size()).forEach(i -> {
                User client = users.get(i);
                Worker worker = workers.get(i);     
                Job job = new Job(client, worker, false, 5.0 - i, "Description" + i, i * 20, new HashSet<>());
                jobRepository.save(job); // Save the job first
                
                // Now create the skill for the job and worker 
                Skill skill = new Skill("Skill" + (i+1)  ,worker,  job);
                skillRepository.save(skill);
                worker.getSkills().add(skill);
                job.getSkills().add(skill);
                skillRepository.save(skill);
             
           
         });
        };
    }

}