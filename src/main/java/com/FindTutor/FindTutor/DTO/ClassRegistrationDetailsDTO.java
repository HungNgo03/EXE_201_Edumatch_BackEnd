package com.FindTutor.FindTutor.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassRegistrationDetailsDTO {
    private Long id;
    private String subjectId;
    private String status;
    private String preferredSchedule;
    private String tutorName;
    private String paymentStatus;
    private LocalDateTime registerAt;
    private String grade;

    public ClassRegistrationDetailsDTO(Long id,String subjectId, String status, String preferredSchedule, String tutorName, LocalDateTime registerAt, String grade,String paymentStatus) {
        this.id = id;
        this.subjectId = subjectId;
        this.status = status;
        this.preferredSchedule = preferredSchedule;
        this.tutorName = tutorName;
        this.registerAt = registerAt;
        this.grade = grade;
        this.paymentStatus = paymentStatus;
    }
}
