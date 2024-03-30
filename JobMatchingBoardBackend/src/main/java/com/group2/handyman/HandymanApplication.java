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

            Map<String, String[]> conversationTopics = new HashMap<>();
            conversationTopics.put("Plumbing", new String[]{
                    "I've noticed my shower drain is clogged. Can you help?",
                    "Sure, I can take a look. Do you know what might be causing the clog?",
                    "Not sure, it just started draining slowly.",
                    "I'll bring the necessary tools to check and clear it up. How about tomorrow afternoon?",
                    "That works for me. Thank you!"
            });
            conversationTopics.put("Electrical", new String[]{
                    "I need some electrical outlets added to my home office.",
                    "No problem, how many outlets are you thinking?",
                    "Maybe two or three, depending on what you think is best.",
                    "I can come by to assess the space and provide a recommendation. Is Wednesday okay?",
                    "Yes, see you then!"
            });
            conversationTopics.put("Landscaping", new String[]{
                    "I'm looking to redo my backyard with some new plants and a patio area.",
                    "Sounds like a great project. Do you have a design in mind or need help with that?",
                    "I'd love some design suggestions.",
                    "Let's schedule a time for me to come over and discuss your vision. How's Friday?",
                    "Perfect, thanks!"
            });

            Faker faker = new Faker();
            Random random = new Random();

            // create users and workers
            IntStream.rangeClosed(1, 30).forEach(i -> {
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String email = faker.internet().emailAddress();
                double credit = 1000 + random.nextDouble() * 9000;

                User user = new User(firstName + "." + lastName, passwordEncoder.encode("password"), email, credit);
                userRepository.save(user);

                Worker worker = new Worker(firstName + "." + lastName, passwordEncoder.encode("password"), email,
                        faker.job().title(), faker.address().cityName(), Math.floor(1 + random.nextDouble() * 4), random.nextInt(100),
                        new HashSet<>(), "09:00-17:00", credit, faker.phoneNumber().cellPhone(),
                        Worker.PreferredCommunication.values()[random.nextInt(Worker.PreferredCommunication.values().length)]);
                workerRepository.save(worker);
            });

            List<User> users = userRepository.findAll();
            List<Worker> workers = workerRepository.findAll();

            // create jobs
            users.forEach(user -> {
                IntStream.rangeClosed(1, random.nextInt(3) + 1).forEach(i -> {
                    Worker worker = workers.get(random.nextInt(workers.size()));
                    boolean isCompleted = random.nextBoolean();
                    String jobTitle = faker.job().title();
                    String jobDescription = "Performing " + jobTitle + " tasks as required.";
                    double budget = Math.round((100.0 + random.nextDouble() * 900.0) * 100.0) / 100.0;
                    Job job = new Job(user, worker, isCompleted, isCompleted ? Math.floor(1 + random.nextDouble() * 4) : null,
                            jobTitle, jobDescription, budget, new HashSet<>());
                    jobRepository.save(job);

                    // create skills
                    Skill skill = new Skill(jobTitle, worker, job);
                    skillRepository.save(skill);
                });
            });

            // Create messages
//            workers.forEach(worker -> {
//                users.forEach(user -> {
//                    if(random.nextBoolean()) {
//                        String content = "I'm interested in discussing your " + faker.job().field() + " needs. When are you available?";
//                        LocalDateTime timestamp = LocalDateTime.now().minusDays(random.nextInt(30));
//                        Message message = new Message(user, worker, content, timestamp);
//                        messageRepository.save(message);
//                    }
//                });
//            });

            workers.forEach(worker -> {
                users.forEach(user -> {
                    if (random.nextBoolean()) { // Randomly decide to create a message or not
                        String jobField = faker.job().field(); // Get a random job field
                        String[] conversation = conversationTopics.getOrDefault(jobField, new String[]{"Hello, how can I assist you today?", "I was wondering if you could help with a project.", "Of course, what do you need help with?", "I'll need some more information to get started.", "Let's arrange a meeting to discuss further."});
                        LocalDateTime timestamp = LocalDateTime.now().minusDays(random.nextInt(7)); // Messages within the last week

                        for (String content : conversation) {
                            Message message = new Message(user, worker, content, timestamp);
                            messageRepository.save(message);
                            timestamp = timestamp.plusMinutes(random.nextInt(10, 30)); // Increment time for conversation flow
                        }
                    }
                });
            });
        };
    }
}