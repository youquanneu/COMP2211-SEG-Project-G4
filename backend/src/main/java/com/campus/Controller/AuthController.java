package com.campus.Controller;

import com.campus.DataTransferObject.User.AuthResponse;
import com.campus.DataTransferObject.User.LoginRequest;
import com.campus.DataTransferObject.User.UserDTO;
import com.campus.Service.Security.JwtService;
import com.campus.Service.User.UserService;
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
    private UserService userService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            UserDTO userDTO = userService.getUserByUsernamePassword(username,password);

            String token = jwtService.generateToken(user);

            return ResponseEntity.ok(new AuthResponse(token,userDTO));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Authentication failed: " + e.getMessage());
        }
    }
}
