package com.FindTutor.FindTutor.DTO;

import lombok.Data;

@Data
public class StudentRequest {
    private int userID;
    private String parentName;
    private String grade;
    private String address;
    private String notes;
}
