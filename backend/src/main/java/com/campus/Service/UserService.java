package com.campus.Service;


import com.campus.Entity.User;
import com.campus.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
    @Override   //Still don't know how to apply
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameEqualsIgnoreCase(username);
        if (user.isPresent()){
            var userL = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userL.getUsername())
                    .password(userL.getPassword())
                    .build();
        }else {
            throw new UsernameNotFoundException(username);
        }
    }
}
