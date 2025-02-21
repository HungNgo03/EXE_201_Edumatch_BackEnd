package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.DTO.LoginRequest;
import com.FindTutor.FindTutor.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:63342",allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Đăng ký tài khoản
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        System.out.println("Received user: " + user.getUsername() + ", Password: " + user.getPasswordHash());
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username '" + user.getUsername() + "' already taken");
        }
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        if (userService.existsByPhoneNumber(user.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Phone number already in use");
        }

        if (user.getPasswordHash() == null) {
            return ResponseEntity.badRequest().body("Password cannot be null");
        }

        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }


    // Đăng nhập tài khoản
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Users user = userService.findByUsername(loginRequest.getUsername());

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        // Lưu user vào session
        session.setAttribute("user", user);

        return ResponseEntity.ok(Map.of(
                "message", "Login Successful",
                "username", user.getUsername()
        ));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}
