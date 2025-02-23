package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Students;
import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Entity.Users;
//import com.FindTutor.FindTutor.Repository.StudentRepository;
import com.FindTutor.FindTutor.Repository.TutorRepository;
import com.FindTutor.FindTutor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

//    @Autowired
//    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updateUser(Users user) {
        userRepository.save(user); // Lưu lại user với mật khẩu mới
    }


    public Users registerUser(Users user) {
        return userRepository.save(user);
    }

    public Optional<Users> findById(int id) {
        return userRepository.findById(id);
    }


    public Users findByUsername(String Username) {
        return userRepository.findByUsername(Username);
    }

    public Users findByEmail(String Email) {
        return userRepository.findByEmail(Email);
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
