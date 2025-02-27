package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.PaymentQrDTO;
import com.FindTutor.FindTutor.DTO.PaymentQrResponse;
import com.FindTutor.FindTutor.DTO.RegisterIdRequestDTO;

public interface IPaymentService {
    void confirmPayment(String json) throws Exception;
    int checkPayment(RegisterIdRequestDTO dto);
}
