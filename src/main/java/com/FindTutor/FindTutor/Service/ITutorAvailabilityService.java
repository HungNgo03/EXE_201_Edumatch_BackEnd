package com.FindTutor.FindTutor.Service;


import com.FindTutor.FindTutor.Entity.TutorAvailability;
import com.FindTutor.FindTutor.DTO.TutorDTO;

import java.util.List;

public interface ITutorAvailabilityService {
    List<TutorAvailability> getAvailability(int tutorId);
}
