package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Repository.TutorRepository;
<<<<<<< Updated upstream
=======
import com.FindTutor.FindTutor.DTO.TutorDTO;
>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TutorService implements ITutorService{
    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public List<Tutors> getAllTutors() {
        return tutorRepository.findByStatus(1);
    }

<<<<<<< Updated upstream
    @Override
    public Tutors getTutorById(Integer id) {
        Optional<Tutors> tutor = tutorRepository.findById(id);
        return tutor.orElse(null);
    }
=======

>>>>>>> Stashed changes
}
