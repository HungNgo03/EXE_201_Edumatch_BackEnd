package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.AdminDTO;
import com.FindTutor.FindTutor.DTO.TutorDTO;
import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.Repository.TutorRepository;
import com.FindTutor.FindTutor.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminService implements IAdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public List<AdminDTO> getAllUsers() {
        return userRepository.findAllUser();
    }

    @Override
    public List<TutorDTO> getAllTutors() {
        return tutorRepository.findAllTutor();
    }
    public Tutors updateTutor(int id, Tutors tutorDetails) {
        Tutors tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found with id: " + id));
        tutor.setStatus(tutorDetails.getStatus());
        return tutorRepository.save(tutor);
    }
}
