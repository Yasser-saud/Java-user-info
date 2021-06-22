package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private final UserRepository userRepository;

	public DemoApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// main sprint boot
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// save 2 users to the database on startup
	@Override
	public void run(String... args) throws Exception {
		User user1 = new User("yasser", "saud");
		User user2 = new User("tarq", "fahad");
		userRepository.save(user1);
		userRepository.save(user2);
	}
}