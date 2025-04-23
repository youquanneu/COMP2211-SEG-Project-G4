package com.campus.Service.User;

import com.campus.Classification.UserRole;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.User.User;
import com.campus.Repository.User.UserRepository;
import com.campus.Service.Mail.EmailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User("john.doe","john.doe@example.com","encodedPassword123", UserRole.AdministrativeStaff);
    }

    @Test
    public void testLoadUserByUsername() {
        when(userRepository.findByUsernameEqualsIgnoreCase("john.doe")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("john.doe");

        assertThat(userDetails.getUsername()).isEqualTo("john.doe");
        assertThat(userDetails.getPassword()).isEqualTo("encodedPassword123");
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserId()).isEqualTo(1);
        assertThat(foundUser.getUsername()).isEqualTo("john.doe");
    }

    @Test
    public void testGetUserByEmail() {
        when(userRepository.findByEmailEqualsIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByEmail("john.doe@example.com");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void testLoginByEmail_Successful() {
        when(userRepository.findByEmailEqualsIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", user.getPassword())).thenReturn(true);

        User loggedInUser = userService.loginByEmail("john.doe@example.com", "password123");

        assertThat(loggedInUser).isNotNull();
        assertThat(loggedInUser.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void testLoginByEmail_InvalidPassword() {
        when(userRepository.findByEmailEqualsIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", user.getPassword())).thenReturn(false);

        assertThatThrownBy(() -> userService.loginByEmail("john.doe@example.com", "wrongPassword"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Password Incorrect");
    }

    @Test
    public void testChangePassword_Successful() {
        when(passwordEncoder.matches("oldPassword123", user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("newPassword123")).thenReturn("encodedNewPassword123");

        userService.changePassword(user, "oldPassword123", "newPassword123", "newPassword123");

        assertThat(user.getPassword()).isEqualTo("encodedNewPassword123");
        verify(userRepository, times(1)).save(user);  // Ensure save was called
    }

    @Test
    public void testForgotPassword_Successful() {
        when(userRepository.findByEmailEqualsIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));
        when(emailSenderService.sendOTP("john.doe@example.com")).thenReturn("123456");

        User resetUser = userService.forgotPassword("john.doe@example.com", "123456");

        assertThat(resetUser).isNotNull();
        assertThat(resetUser.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void testForgotPassword_FailedOTP() {
        when(userRepository.findByEmailEqualsIgnoreCase("john.doe@example.com")).thenReturn(Optional.of(user));
        when(emailSenderService.sendOTP("john.doe@example.com")).thenReturn("123456");

        assertThatThrownBy(() -> userService.forgotPassword("john.doe@example.com", "wrongOTP"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("OTP not matches");
    }
}
