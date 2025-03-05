package com.campus;

import com.campus.Entity.User;
import com.campus.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SmartCampusBackendApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(SmartCampusBackendApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;
	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input username : ");
		String username = scanner.nextLine();
		System.out.println("Input email : ");
		String email = scanner.nextLine();
		System.out.println("Input password : ");
		String password = scanner.nextLine();
		User newUser = new User(username,email,password);
		userRepository.save(newUser);
		System.out.println("Adding new user : " + newUser.getUsername());
	}
}
