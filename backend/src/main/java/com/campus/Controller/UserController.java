package com.campus.Controller;

import com.campus.Entity.User;
import com.campus.Repository.UserRepository;
import com.campus.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController implements CommandLineRunner{
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }
    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }
    @Override
    public void run(String... args) throws Exception {
        userService.register();
        userService.login();
    }
}
