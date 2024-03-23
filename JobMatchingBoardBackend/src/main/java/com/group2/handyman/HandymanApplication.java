package com.group2.handyman;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.group2.handyman.controller.NotificationService;
import com.group2.handyman.model.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class HandymanApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandymanApplication.class, args);
    }

    @Bean
    public ApplicationRunner init(UserRepository userRepository, WorkerRepository workerRepository,
                                  JobRepository jobRepository, SkillRepository skillRepository,
                                  PasswordEncoder passwordEncoder, MessageRepository messageRepository) {
        return args -> {
            List<String> firstNames = Arrays.asList("John", "Jane", "Mike", "Sara", "Alex", "Emma", "Noah", "Olivia", "Liam", "Sophia");
            List<String> lastNames = Arrays.asList("Doe", "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez");
            List<String> locations = Arrays.asList("New York", "San Francisco", "Chicago", "Seattle", "Boston", "Los Angeles", "Denver", "Austin", "Miami", "Las Vegas");
            List<String> skills = Arrays.asList("Plumbing", "Electrical", "Carpentry", "Landscaping", "Painting", "Cleaning", "Roofing", "Flooring", "Tiling", "Decorating");
            List<String> jobDescriptions = Arrays.asList("Fix bathroom leak", "Install ceiling fan", "Build a deck", "Design garden", "Paint living room", "Clean the garage", "Replace roof shingles", "Install hardwood floors", "Tile the kitchen backsplash", "Decorate the office");
            Worker.PreferredCommunication[] communicationPreferences = Worker.PreferredCommunication.values();

            Random random = new Random(123456789);

            // Create users
            for (int i = 0; i < 10; i++) {
                User user = new User("user_" + firstNames.get(i) + lastNames.get(i), passwordEncoder.encode("password" + i), "user" + i + "@example.com", 100 + i * 100);
                userRepository.save(user);
            }

            // Create workers
            List<Worker> allWorkers = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Worker worker = new Worker("worker_" + firstNames.get(i) + lastNames.get(i),
                        passwordEncoder.encode("password" + i), "worker" + i + "@example.com",
                        "Expert in " + skills.get(i), locations.get(i),
                        4.0 + (i % 5), 10 + i,
                        new HashSet<>(), "09:00-17:00", 100 + i * 100,
                        "555-01" + String.format("%02d", i), communicationPreferences[i % 2]);
                allWorkers.add(workerRepository.save(worker));
            }

            // Create jobs and link them with workers and users, then create skills linked to jobs and workers
            allWorkers.forEach(worker -> {
                IntStream.rangeClosed(1, 2).forEach(i -> {
                    User user = userRepository.findById((long) random.nextInt(10) + 1).get();
                    boolean isCompleted = random.nextBoolean();
                    Job job = new Job(userRepository.findById((long) (i % 10 + 1)).get(), worker, isCompleted,
                            isCompleted ? 4.0 + i % 5 : null,
                            jobDescriptions.get(i), 100 + i * 10,
                            new HashSet<>());
                            job = jobRepository.save(job);

                    // Assuming each job has 1 skill for simplification
                    Skill skill = new Skill(skills.get(random.nextInt(skills.size())), worker, job);
                    skillRepository.save(skill);

                    worker.getSkills().add(skill);
                    job.getSkills().add(skill);

                    workerRepository.save(worker);
                    jobRepository.save(job);

                    // Transaction history for completed jobs
                    if (isCompleted) {
                        String transaction = "Completed job '" + job.getDescription() + "' for $" + job.getBudget();
                        worker.getPreviousTransactions().add(transaction);
                        workerRepository.save(worker);
                    }
                });
            });

            // Create messages
            for (int i = 0; i < 10; i++) {
                Worker worker = workerRepository.findById((long) (i % 10 + 1)).get();
                User user = userRepository.findById((long) ((i + 1) % 10 + 1)).get();
                LocalDateTime timestamp = LocalDateTime.now().minusDays(i % 30);
                String content = "Can we schedule a visit for " + jobDescriptions.get(i) + "?";
                Message message = new Message(user, worker, content, timestamp);
                messageRepository.save(message);
            }
        };
    }

}
