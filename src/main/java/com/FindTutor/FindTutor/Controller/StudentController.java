package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.Entity.Students;
import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.DTO.StudentRequest;
import com.FindTutor.FindTutor.Repository.StudentRepository;
import com.FindTutor.FindTutor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentRepository studentsRepository;

    @Autowired
    private UserRepository usersRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRequest studentRequest) {
        Optional<Users> userOptional = usersRepository.findById(studentRequest.getUserID());

        if (userOptional.isPresent() && "Student".equals(userOptional.get().getRole())) {
            Students student = new Students();
            student.setUserID(studentRequest.getUserID());
            student.setParentName(studentRequest.getParentName());
            student.setGrade(studentRequest.getGrade());
            student.setAddress(studentRequest.getAddress());
            student.setNotes(studentRequest.getNotes());

            studentsRepository.save(student);
            return ResponseEntity.ok("Student registered successfully");
        }
        return ResponseEntity.badRequest().body("User not found or not a Student");
    }
}
