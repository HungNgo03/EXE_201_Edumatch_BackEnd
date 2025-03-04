package com.FindTutor.FindTutor.DTO;

import lombok.Data;

@Data
public class ClassListDTO {
    private String className;
    private String tutorName;

    public ClassListDTO(String className, String tutorName) {
        this.className = className;
        this.tutorName = tutorName;
    }
}
