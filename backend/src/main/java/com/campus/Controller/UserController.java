package com.campus.Controller;

import com.campus.Entity.User;
import com.campus.Repository.UserRepository;
import com.campus.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@RestController
@RequestMapping("/user")
public class UserController implements CommandLineRunner{
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }
    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }


    @Override // Function waiting of polish
    public void run(String... args) throws Exception {
        registerNewUser();
        loginAsUser();

    }
    private void registerNewUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input username : ");
        String username = scanner.nextLine();
        System.out.println("Input email : ");
        String email = scanner.nextLine();
        System.out.println("Input password : ");
        String password = passwordEncoder.encode(scanner.nextLine());
        User newUser = new User(username,email,password);
        userRepository.save(newUser);
        System.out.println("Adding new user : " + newUser.getUsername());
    }
    private void loginAsUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input username : ");
        String username = scanner.nextLine();
        Optional<User> user = userRepository.findByUsernameEqualsIgnoreCase(username);
        if (user.isPresent()){
            User user1 = user.get();
            System.out.println("Input password : ");

            String password = passwordEncoder.encode(scanner.nextLine());
            if (password.equals(user1.getPassword())){
                System.out.println("Password correct");
            }
            else {
                System.out.println("Wrong password");
            }
        }else {
            System.out.println("Username not found");
        }
    }
    @GetMapping("/test")   //Connection to HTML file?
    public String test(){
        return "test";
    }
}
