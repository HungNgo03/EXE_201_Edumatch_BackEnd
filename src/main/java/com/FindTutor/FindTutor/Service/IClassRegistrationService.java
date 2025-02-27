package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.PaymentQrDTO;
import com.FindTutor.FindTutor.DTO.PaymentQrResponse;
import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import com.FindTutor.FindTutor.DTO.ClassRegistrationRequestDTO;

public interface IClassRegistrationService {
    PaymentQrResponse registerStudent(ClassRegistrationRequestDTO request);
}
