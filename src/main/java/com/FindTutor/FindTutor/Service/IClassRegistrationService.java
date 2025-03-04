package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.ClassRegistrationDetailsDTO;
import com.FindTutor.FindTutor.DTO.PaymentQrDTO;
import com.FindTutor.FindTutor.DTO.PaymentQrResponse;
import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import com.FindTutor.FindTutor.DTO.ClassRegistrationRequestDTO;
import com.FindTutor.FindTutor.Repository.ClassRegistrationRepository;

import java.util.List;

public interface IClassRegistrationService {
    ClassRegistrations registerStudent(ClassRegistrationRequestDTO request);
    List<ClassRegistrationDetailsDTO> getRegistration(int userId);
    String getPaymentQr(int registrationId);

}
