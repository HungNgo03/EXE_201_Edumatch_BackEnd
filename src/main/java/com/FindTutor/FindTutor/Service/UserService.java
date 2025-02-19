package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.Repository.UserRepository;
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
    public boolean existsByPhoneNumber(String PhoneNumber) {
        return userRepository.existsByPhoneNumber(PhoneNumber);
    }

    public boolean existsByEmail(String Email) {
        return userRepository.existsByEmail(Email);
    }
}
