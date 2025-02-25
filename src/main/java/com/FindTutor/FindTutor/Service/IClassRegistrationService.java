package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import com.FindTutor.FindTutor.dto.ClassRegistrationRequestDTO;

public interface IClassRegistrationService {
    void registerStudent(ClassRegistrationRequestDTO request);
}
