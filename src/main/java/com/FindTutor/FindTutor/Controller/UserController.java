package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.DTO.LoginRequest;
import com.FindTutor.FindTutor.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username '" + user.getUsername() + "' already taken");
        }
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        // Mã hóa mật khẩu
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // Đăng nhập tài khoản

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        Users user = userService.findByUsername(loginRequest.getUsername());

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        return ResponseEntity.ok("Login Successful");
    }
}
