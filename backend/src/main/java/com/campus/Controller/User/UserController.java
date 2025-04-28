package com.campus.Controller.User;

import com.campus.DataTransferObject.Mail.EmailDTO;
import com.campus.DataTransferObject.Mail.OneTimePasswordDTO;
import com.campus.DataTransferObject.User.LoginRequest;
import com.campus.DataTransferObject.User.ResetPasswordRequest;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.DataTransferObject.User.UserEmailDTO;
import com.campus.Service.Mail.EmailSenderService;
import com.campus.Service.Mail.OneTimePasswordService;
import com.campus.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController{
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private OneTimePasswordService oneTimePasswordService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Login processing : " + loginRequest);
        try {
            UserDTO userDTO = UserDTO.mapper(
                    userService.login(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()));
            logger.info("Get userDTO" + userDTO.getUsername());
            return ResponseEntity.ok(userDTO);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/userProfile")
    public ResponseEntity<?> getUserProfile(@RequestBody UserEmailDTO userEmailDTO) {
        logger.info("Processing getUserProfile ");
        try {
            UserDTO userDTO = UserDTO.mapper(userService.getUserByEmail(userEmailDTO.getEmail()));
            logger.info("Get userDTO" + userDTO.getUsername());
            return ResponseEntity.ok(userDTO);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/requestOtp")
    public ResponseEntity<?> requestOTP(@RequestBody EmailDTO emailDTO){
        logger.info("Sending OTP to : " + emailDTO);
        try {
            String prefixToShow = emailSenderService.sendOTP(emailDTO.getEmail());
            logger.info("OTP with Prefix : " + prefixToShow + " successfully sent to " + emailDTO);
            return ResponseEntity.ok(prefixToShow);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/matchOtp")
    public ResponseEntity<?> matchOTP(@RequestBody OneTimePasswordDTO oneTimePasswordDTO){
        logger.info("Matching OTP : " + oneTimePasswordDTO.getEmail() + " , " + oneTimePasswordDTO.getOtpPrefix() + "-" + oneTimePasswordDTO.getEnteredOTP());
        try {
            UserDTO userDTO = UserDTO.mapper(
                    oneTimePasswordService.matchOneTimePassword(
                    oneTimePasswordDTO.getEmail(),
                    oneTimePasswordDTO.getOtpPrefix(),
                    oneTimePasswordDTO.getEnteredOTP()));
            logger.info("Match successful for : " + userDTO.getUsername());
            return ResponseEntity.ok(userDTO);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        logger.info("Reset password for : " + resetPasswordRequest.getEmail());
        try {
            UserDTO userDTO = UserDTO.mapper(userService.changeToNewPassword(
                    userService.getUserByEmail(resetPasswordRequest.getEmail()),
                    resetPasswordRequest.getNewPassword(),
                    resetPasswordRequest.getConfirmPassword()));
            logger.info("Password reset successful for : " + userDTO.getUsername());
            return ResponseEntity.ok(userDTO);
        }catch (Exception e){
            logger.info("Get exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
