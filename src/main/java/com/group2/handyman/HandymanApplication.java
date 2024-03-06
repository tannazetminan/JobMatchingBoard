package com.group2.handyman;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.group2.handyman.model.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class HandymanApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandymanApplication.class, args);
    }


    @Bean
    public ApplicationRunner init(UserRepository userRepository, WorkerRepository workerRepository,
                                  JobRepository jobRepository, SkillRepository skillRepository,
                                  PasswordEncoder passwordEncoder, MessageRepository messageRepository) {
        return args -> {

            // create random users and workers for database
            String[] firstNames = {"John", "Jane", "Mike", "Sara", "Alex"};
            String[] lastNames = {"Doe", "Smith", "Johnson", "Williams", "Brown"};
            String[] locations = {"New York", "San Francisco", "Chicago", "Seattle", "Boston"};
            String[] skills = {"Plumbing", "Electrical", "Carpentry", "Landscaping", "Painting"};
            String[] jobDescriptions = {"Fix bathroom leak", "Install ceiling fan", "Build a deck", "Design garden", "Paint living room"};
            String[] messages = {
                    "Hey, when can you start?",
                    "Can you send me a quote?",
                    "Thank you for your quick work!",
                    "Do you need any more details from me?",
                    "I'm available next week, does that work for you?"
            };
            Random random = new Random();

            // create users for database
            List<User> users = IntStream.rangeClosed(1, 5)
                    .mapToObj(i -> {
                        String name = firstNames[random.nextInt(firstNames.length)] + lastNames[random.nextInt(lastNames.length)];
                        return new User(name.toLowerCase(), passwordEncoder.encode("pass" + i), name.toLowerCase() + "@example.com");
                    })
                    .map(userRepository::save)
                    .collect(Collectors.toList());

            // create workers for database
            List<Worker> workers = IntStream.rangeClosed(1, 5)
                    .mapToObj(i -> {
                        String name = firstNames[random.nextInt(firstNames.length)] + lastNames[random.nextInt(lastNames.length)];
                        String location = locations[random.nextInt(locations.length)];
                        return new Worker(name.toLowerCase(), passwordEncoder.encode("worker" + i), name.toLowerCase() + "@example.com", "Expert in " + skills[i % skills.length], location, 4.0 + (random.nextDouble() * (5.0 - 4.0)), i, new HashSet<>(), "2024-01-01");
                    })
                    .map(workerRepository::save)
                    .collect(Collectors.toList());

            IntStream.range(0, users.size()).forEach(i -> {
                User client = users.get(i);
                Worker worker = workers.get(i);
                String description = jobDescriptions[i % jobDescriptions.length];
                Job job = new Job(client, worker, random.nextBoolean(), 4.0 + (random.nextDouble() * (5.0 - 4.0)), description, i * 100 + 50, new HashSet<>());
                jobRepository.save(job);

                // create skills for database
                Skill skill = new Skill(skills[i % skills.length], worker, job);
                skillRepository.save(skill);
                worker.getSkills().add(skill);
                job.getSkills().add(skill);
                skillRepository.save(skill);
            });

            // create messages for database
            IntStream.rangeClosed(1, 10).forEach(i -> { // Create 10 messages
                User user = users.get(random.nextInt(users.size())); // Random user
                Worker worker = workers.get(random.nextInt(workers.size())); // Random worker
                String content = messages[random.nextInt(messages.length)]; // Random message from the array
                LocalDateTime timestamp = LocalDateTime.now().minusDays(random.nextInt(30)); // Random timestamp within the last 30 days

                Message message = new Message(user, worker, content, timestamp);
                messageRepository.save(message); // Save each message to the database
            });
        };
    }

}