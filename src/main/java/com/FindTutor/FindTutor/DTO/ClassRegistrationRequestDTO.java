package com.FindTutor.FindTutor.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassRegistrationRequestDTO {
    private String userName;
    private Integer classId;
    private String subjectId;
    private String grade;
    private String preferredSchedule;
    private int tutorId;
}
