package com.campus.Controller;

import com.campus.DataTransferObject.User.AuthResponse;
import com.campus.DataTransferObject.User.LoginRequest;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Entity.User.User;
import com.campus.Service.Security.JwtService;
import com.campus.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.info("Login by email : " + request.getEmail());
        try {
            User userByEmail = userService.loginByEmail(request.getEmail(),request.getPassword());
            logger.info("Login User : " + userByEmail.toString());
            String username = userByEmail.getUsername();
            String password = request.getPassword();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );
            logger.info("Authentication : " + authentication.getPrincipal() );
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String token = jwtService.generateToken(user);
            logger.info("JWT token : "+ token );
            UserDTO userDTO = UserDTO.mapper(userByEmail);
            return ResponseEntity.ok(new AuthResponse(token,userDTO));
        }
        catch (Exception e) {
            logger.info("Login failed : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
