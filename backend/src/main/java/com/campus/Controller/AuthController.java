package com.campus.Controller;

import com.campus.Security.JwtService;
import com.campus.Entity.User;
import com.campus.Service.UserService;
import com.campus.dto.LoginRequest;
import com.campus.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserService userService,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            
            org.springframework.security.core.userdetails.User user = 
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
                    
            User userEntity = userService.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
                    
            String token = jwtService.generateToken(user);
            
            return ResponseEntity.ok(new AuthResponse(
                    token,
                    userEntity.getUserId(),
                    userEntity.getUsername(),
                    userEntity.getEmail(),
                    userEntity.getUserRole()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Authentication failed: " + e.getMessage());
        }
    }
}