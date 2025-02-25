package com.FindTutor.FindTutor.dto;

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
}
