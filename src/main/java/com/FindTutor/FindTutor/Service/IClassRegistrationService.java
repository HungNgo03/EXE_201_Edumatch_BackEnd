package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.*;
import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import com.FindTutor.FindTutor.Repository.ClassRegistrationRepository;

import java.util.Date;
import java.util.List;

public interface IClassRegistrationService {
    ClassRegistrations registerStudent(ClassRegistrationRequestDTO request);
    List<ClassRegistrationDetailsDTO> getRegistration(int userId);
    String getPaymentQr(int registrationId);

    void createNewClass(CreateClassRequestDTO requestDTO);
}
