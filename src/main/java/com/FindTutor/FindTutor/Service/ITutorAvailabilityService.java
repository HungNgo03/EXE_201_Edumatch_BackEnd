package com.FindTutor.FindTutor.Service;


import com.FindTutor.FindTutor.Entity.TutorAvailability;
import com.FindTutor.FindTutor.dto.TutorDTO;

import java.util.List;

public interface ITutorAvailabilityService {
    List<TutorAvailability> getAvailability(int tutorId);
}
