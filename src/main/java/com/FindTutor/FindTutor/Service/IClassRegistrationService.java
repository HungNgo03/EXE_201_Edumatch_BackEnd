package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import com.FindTutor.FindTutor.DTO.ClassRegistrationRequestDTO;

public interface IClassRegistrationService {
    void registerStudent(ClassRegistrationRequestDTO request);
}
