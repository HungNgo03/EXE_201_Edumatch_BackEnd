package com.FindTutor.FindTutor.DTO;

import lombok.Data;

import java.sql.Date;

@Data
public class TutorRequest {
    private int userID;
    private boolean gender;
    private Date dateOfBirth;
    private String address;
    private String qualification;
    private int experience;
    private String bio;
}
