package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.AdminDTO;
import com.FindTutor.FindTutor.DTO.TutorDTO;
import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Entity.Users;

import java.util.List;

public interface IAdminService {
    List<AdminDTO> getAllUsers();
    List<TutorDTO> getAllTutors();
    Tutors updateTutor(int id, Tutors tutor);
}
