package com.campus.Service.User;

import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.User.User;

import com.campus.Repository.User.UserRepository;
import com.campus.Service.Mail.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    @Autowired
    private UserRepository userRepository;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSenderService emailSenderService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
    public User revMapper(UserDTO userDTO){
        return getUserById(userDTO.getUserId());
    }
    public User login(String email, String password){
        logger.info("Email : " + email);
        return loginByEmail(email,password);
    }
    public User getUserByEmail(String email){
        return findUserByEmail(email);
    }
    public User getUserById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return user.get();
    }   // Get user by user id
    public User loginByUsername(String username, String password) {
        User user = findUserByUsername(username);
        verifyCurrentPassword(user,password);
        return user;
    }   // Function: Return a user by username and password
    public User loginByEmail(String email, String password) {
        User user = findUserByEmail(email);
        verifyCurrentPassword(user,password);
        return user;
    }   // Function: Return a user by email and password
    public User findUserByUsername(String username){
        Optional<User> user = userRepository.findByUsernameEqualsIgnoreCase(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return user.get();
    }   // Base Function : Get user by username
    public User findUserBySimilarUsername(String username){
        Optional<User> user = userRepository.findByUsernameContainingIgnoreCase(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return user.get();
    }   // Base Function : Get user by similar username
    public User findUserByEmail(String email){
        Optional<User> user = userRepository.findByEmailEqualsIgnoreCase(email);
        if (user.isEmpty()){
            throw new RuntimeException("User email not found : " + email);
        }
        return user.get();
    }   // Base Function : Get user by email
    public List<User> getUserByUsernameSearching(String username){
        return userRepository.searchByUsernameContainingIgnoreCase(username);
    }
    private void verifyCurrentPassword(User user, String password){
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Password Incorrect");
        }
    }   // Function : Verify current password
    public void changePassword(User user, String currentPassword, String newPassword, String confirmationPassword) {
        verifyCurrentPassword(user,currentPassword);
        changeToNewPassword(user,newPassword,confirmationPassword);
    }   // Function: Change password for user
    public User changeToNewPassword(User user, String newPassword, String confirmationPassword) {
        verifyNewPassword(user, newPassword, confirmationPassword);
        String encoderNewPassword = passwordEncoder.encode(newPassword);
        user.changePassword(encoderNewPassword);
        return userRepository.save(user);  // Save password changed of user into database after validation
    }   // Change password after new verification
    private void verifyNewPassword(User user, String newPassword, String confirmationPassword){
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("New password cannot be the same as current password.");
        }   // Check if new password same as previous password
        else if (!newPassword.equals(confirmationPassword)){
            throw new RuntimeException("Password not matches");
        }   // Check if new password and new password confirmation are same
    }   // Return new password after validation checking
    public User forgotPassword(String email, String inputOTP){
        User user = findUserByEmail(email);
        String givenOTP = emailSenderService.sendOTP(email);
        matchOTP(givenOTP,inputOTP);
        return user;
    }
    private void matchOTP(String givenOTP, String inputOTP){
        if (!givenOTP.equals(inputOTP)){
            throw new RuntimeException("OTP not matches");
        }
    }   // Change password if OTP verification successful
}
