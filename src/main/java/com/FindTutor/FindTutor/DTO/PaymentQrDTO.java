package com.FindTutor.FindTutor.DTO;

import lombok.Data;

@Data
public class PaymentQrDTO {
    private int userId;
    private int tutorId;
    private String subjectId;
}
