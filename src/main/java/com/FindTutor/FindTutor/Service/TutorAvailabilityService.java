package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.TutorAvailability;
import com.FindTutor.FindTutor.Repository.TutorAvailabilityRepository;
import com.FindTutor.FindTutor.Repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TutorAvailabilityService implements ITutorAvailabilityService{
    @Autowired
    private TutorAvailabilityRepository tutorRepository;
    @Override
    public List<TutorAvailability> getAvailability(int tutorId) {
        return tutorRepository.getTutorAvailabilitiesByTutorID(tutorId);
    }
}
