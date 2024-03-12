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
            String[] jobDescriptions = {
                    "Fix bathroom leak", "Install ceiling fan", "Build a deck", "Design garden", "Paint living room",
                    "Clean the garage", "Replace roof shingles", "Install hardwood floors", "Tile the kitchen backsplash", "Decorate the office"
            };
            String[][] messagesContent = {
                    {"Hey, when can you start?", "Can you send me a quote?", "Thank you for your quick work!", "Do you need any more details from me?", "I'm available next week, does that work for you?"},
                    {"Can you handle electrical repairs?", "How much do you charge per hour?", "Your work on my neighbor's house was impressive.", "I have an urgent repair, are you available?", "I'd like to schedule a regular maintenance."},
                    {"Do you offer landscaping services?", "I need my living room painted.", "Can you fix a leaking roof?", "We're renovating our kitchen, can you help?", "Looking for someone to clean my yard."}
            };
            Random random = new Random();

            // Create users
            List<User> users = IntStream.rangeClosed(1, 10)
                    .mapToObj(i -> new User(firstNames[random.nextInt(firstNames.length)] + i, passwordEncoder.encode("pass" + i), "user" + i + "@example.com", random.nextDouble() * 1000))
                    .map(userRepository::save)
                    .toList();

            // Create workers
            List<Worker> workers = IntStream.rangeClosed(1, 10)
                    .mapToObj(i -> new Worker(firstNames[random.nextInt(firstNames.length)] + i, passwordEncoder.encode("worker" + i), "worker" + i + "@example.com",
                            "Expert in " + skills[random.nextInt(skills.length)], locations[random.nextInt(locations.length)], random.nextDouble() * 5, random.nextInt(100),
                            new HashSet<>(), "09:00-17:00", random.nextDouble() * 1000))
                    .map(workerRepository::save)
                    .toList();

            // Create jobs and skills
            users.forEach(user -> {
                IntStream.rangeClosed(1, 2).forEach(i -> {
                    Worker worker = workers.get(random.nextInt(workers.size()));
                    boolean isCompleted = random.nextBoolean();
                    Job job = new Job(user, worker, isCompleted, isCompleted ? 1.0 + (random.nextDouble() * 4.0) : null,
                            jobDescriptions[random.nextInt(jobDescriptions.length)], 100.0 + (random.nextDouble() * 900.0), new HashSet<>());
                    job = jobRepository.save(job);

                    Skill skill = new Skill(skills[random.nextInt(skills.length)], worker, job);
                    skillRepository.save(skill);
                    worker.getSkills().add(skill);
                    job.getSkills().add(skill);
                    workerRepository.save(worker);
                    jobRepository.save(job);
                });
            });

            // Create messages
            users.forEach(user -> {
                Worker worker = workers.get(random.nextInt(workers.size()));
                String[] selectedMessages = messagesContent[random.nextInt(messagesContent.length)];
                IntStream.rangeClosed(1, selectedMessages.length).forEach(i -> {
                    String content = selectedMessages[random.nextInt(selectedMessages.length)];
                    LocalDateTime timestamp = LocalDateTime.now().minusDays(random.nextInt(30));
                    Message message = new Message(user, worker, content, timestamp);
                    messageRepository.save(message);
                });
            });
        };
    }

}