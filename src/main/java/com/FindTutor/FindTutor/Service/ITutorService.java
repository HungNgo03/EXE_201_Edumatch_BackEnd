package com.FindTutor.FindTutor.Service;


import com.FindTutor.FindTutor.dto.TutorDTO;
import com.FindTutor.FindTutor.dto.TutorDetailDTO;

import java.util.List;

public interface ITutorService {
    List<TutorDTO> getAllTutors(String name, String subject);
    TutorDetailDTO getTutorDetail(int tutorId);
}
