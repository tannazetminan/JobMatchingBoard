package com.group2.handyman;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;
import net.datafaker.Faker;
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
            Faker faker = new Faker();
            Random random = new Random();
            List<String> skillsList = Arrays.asList("Carpentry", "Plumbing", "Electrical", "Masonry", "Gardening");

            // Message templates by skill
            Map<String, String[]> messageTemplates = new HashMap<>();
            messageTemplates.put("Carpentry", new String[]{
                    "Can you help with a custom shelving unit?",
                    "I'm available next week to discuss the project.",
                    "What materials would we need for that?"
            });
            messageTemplates.put("Plumbing", new String[]{
                    "My kitchen sink is leaking. Can you fix it?",
                    "Do you have any availability this week?",
                    "I'll need to inspect the sink first, but I can definitely help."
            });
            messageTemplates.put("Electrical", new String[]{
                    "I need some outdoor lights installed.",
                    "Can you do electrical installations?",
                    "Yes, I can. Do you have the lights, or should I provide them?"
            });
            messageTemplates.put("Masonry", new String[]{
                    "I'm looking to build a brick patio.",
                    "That sounds like a great project. Do you have a design in mind?",
                    "Not yet, I was hoping to get some ideas from you."
            });
            messageTemplates.put("Gardening", new String[]{
                    "I want to redesign my garden, can you help?",
                    "Of course, I love working on new garden designs.",
                    "Great, I'm looking for something low maintenance."
            });

            // Skill descriptions
            Map<String, String> skillDescriptions = Map.of(
                    "Carpentry", "Specializes in wooden structures and furniture.",
                    "Plumbing", "Expert in installing and repairing piping systems.",
                    "Electrical", "Qualified to handle electrical systems and fixtures.",
                    "Masonry", "Skilled in building structures using bricks and stones.",
                    "Gardening", "Experienced in cultivating and managing gardens."
            );

            // Create 20 unique users
            IntStream.rangeClosed(1, 20).forEach(i -> {
                User user = new User(faker.name().username().toLowerCase(), passwordEncoder.encode("password"), faker.internet().emailAddress(), faker.number().randomDouble(2, 1000, 5000));
                userRepository.save(user);
            });

            // Create 20 unique workers, each with exactly one skill
            IntStream.rangeClosed(1, 20).forEach(i -> {
                String skillName = skillsList.get(i % skillsList.size());
                String skillDescription = skillDescriptions.get(skillName);

                Worker worker = new Worker(
                        faker.name().fullName().replace(" ", ".").toLowerCase(),
                        passwordEncoder.encode("password"),
                        faker.internet().emailAddress(),
                        skillDescription,
                        faker.address().city(),
                        Math.floor(1 + random.nextDouble() * 5),
                        random.nextInt(100),
                        new HashSet<>(),
                        "09:00-17:00",
                        1000 + random.nextDouble() * 9000,
                        faker.phoneNumber().cellPhone(),
                        Worker.PreferredCommunication.values()[random.nextInt(Worker.PreferredCommunication.values().length)]
                );

                worker = workerRepository.save(worker);

                Skill workerSkill = new Skill(skillName, worker, null);
                workerSkill = skillRepository.save(workerSkill);

                worker.getSkills().add(workerSkill);
                workerRepository.save(worker);
            });

            List<User> users = userRepository.findAll();
            List<Worker> workers = workerRepository.findAll();

            // Create jobs with linked skills
            for (int i = 0; i < 50; i++) {
                User user = users.get(random.nextInt(users.size()));
                Worker worker = workers.get(random.nextInt(workers.size()));
                boolean isCompleted = random.nextBoolean();
                String skill = skillsList.get(random.nextInt(skillsList.size()));
                String jobTitle = skill;
                String jobDescription = "A " + skill.toLowerCase() + " job requiring attention to detail.";

                Job job = new Job(user, worker, isCompleted, isCompleted ? Math.floor(random.nextDouble() * 5 + 1) : null,
                        jobTitle, jobDescription, Math.round((100.0 + random.nextDouble() * 400.0) * 100.0) / 100.0,
                        new HashSet<>());
                job = jobRepository.save(job);

                Skill jobSkill = new Skill(skill, worker, job);
                skillRepository.save(jobSkill);
            }

            // Create meaningful messages between users and workers
            workers.forEach(worker -> {
                users.forEach(user -> {
                    if (random.nextBoolean()) {
                        LocalDateTime timestamp = LocalDateTime.now().minusDays(random.nextInt(30) + 1);
                        String skill = skillsList.get(random.nextInt(skillsList.size()));
                        String[] messages = messageTemplates.get(skill);

                        for (String messageText : messages) {
                            Message message = new Message(user, worker, messageText, timestamp);
                            messageRepository.save(message);
                            timestamp = timestamp.plusMinutes(15 + random.nextInt(45)); // Increment time for next message
                        }
                    }
                });
            });
        };
    }
}