package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users registerUser(Users user) {
        return userRepository.save(user);
    }

    public Users findByUsername(String Username) {
        return userRepository.findByUsername(Username);
    }

    public boolean existsByUsername(String Username) {
        return userRepository.existsByUsername(Username);
    }

    public boolean existsByEmail(String Email) {
        return userRepository.existsByEmail(Email);
    }
}
