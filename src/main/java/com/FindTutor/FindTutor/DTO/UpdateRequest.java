package com.FindTutor.FindTutor.DTO;

import lombok.Data;
import java.sql.Date;

@Data
public class UpdateRequest {
    // Thông tin User
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String role;

    // Nếu là Tutor
    private Boolean gender;
    private Date dateOfBirth;
    private String address;
    private String qualification;
    private int experience;
    private String bio;

    // Nếu là Student
    private String parentName;
    private String grade;
    private String notes;
}

