package com.FindTutor.FindTutor.Service;


import com.FindTutor.FindTutor.DTO.ScheduleDTO;
import com.FindTutor.FindTutor.DTO.TutorDTO;


import com.FindTutor.FindTutor.DTO.TutorDetailDTO;


import java.util.List;

public interface ITutorService {
    List<TutorDTO> getAllTutors(String name, String subject);
    List<ScheduleDTO> getTutorSchedule(int tutorId);
}
