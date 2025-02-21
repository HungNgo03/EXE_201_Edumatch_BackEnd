package com.FindTutor.FindTutor.Service;


import com.FindTutor.FindTutor.dto.TutorDTO;

import java.util.List;

public interface ITutorService {
    List<TutorDTO> getAllTutors(String name, String subject);
}
