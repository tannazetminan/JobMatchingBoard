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
            String[] firstNames = {"John", "Jane", "Mike", "Sara", "Alex", "Emma", "Noah", "Olivia", "Liam", "Sophia"};
            String[] lastNames = {"Doe", "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez"};
            String[] locations = {"New York", "San Francisco", "Chicago", "Seattle", "Boston", "Los Angeles", "Denver", "Austin", "Miami", "Las Vegas"};
            String[] skills = {"Plumbing", "Electrical", "Carpentry", "Landscaping", "Painting", "Cleaning", "Roofing", "Flooring", "Tiling", "Decorating"};
            String[] jobDescriptions = {"Fix bathroom leak", "Install ceiling fan", "Build a deck", "Design garden", "Paint living room", "Clean the garage", "Replace roof shingles", "Install hardwood floors", "Tile the kitchen backsplash", "Decorate the office"};
            Worker.PreferredCommunication[] communicationPreferences = Worker.PreferredCommunication.values();
            Random random = new Random();

            // Create users
            List<User> users = IntStream.rangeClosed(1, 10)
                    .mapToObj(i -> new User(firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)], passwordEncoder.encode("pass" + i), "user" + i + "@example.com", 100 + random.nextDouble() * 900))
                    .map(userRepository::save)
                    .collect(Collectors.toList());

            // Create workers
            List<Worker> workers = IntStream.rangeClosed(1, 10)
                    .mapToObj(i -> {
                        Worker worker = new Worker(firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)],
                                passwordEncoder.encode("worker" + i), "worker" + i + "@example.com",
                                "Expert in " + skills[random.nextInt(skills.length)], locations[random.nextInt(locations.length)],
                                random.nextDouble() * 5, random.nextInt(100),
                                new HashSet<>(), "09:00-17:00", 100 + random.nextDouble() * 900,
                                "555-01" + String.format("%02d", i), communicationPreferences[random.nextInt(communicationPreferences.length)]);
                        return workerRepository.save(worker);
                    })
                    .collect(Collectors.toList());

            // Adjust job creation to link with workers
            for (Worker worker : workers) {
                Job job = new Job(users.get(random.nextInt(users.size())), worker, random.nextBoolean(),
                        random.nextDouble() * 5, jobDescriptions[random.nextInt(jobDescriptions.length)],
                        50 + random.nextDouble() * 150, new HashSet<>());
                jobRepository.save(job);

                String transaction = "Completed job '" + job.getDescription() + "' for $" + job.getBudget();
                worker.getPreviousTransactions().add(transaction);
                workerRepository.save(worker);
            }

            // Create Message
            users.forEach(user -> {
                Worker worker = workers.get(random.nextInt(workers.size()));
                LocalDateTime timestamp = LocalDateTime.now().minusDays(random.nextInt(30));
                String content = "Can we schedule a visit for " + jobDescriptions[random.nextInt(jobDescriptions.length)] + "?";
                Message message = new Message(user, worker, content, timestamp);
                messageRepository.save(message);
            });
        };
    }
}
