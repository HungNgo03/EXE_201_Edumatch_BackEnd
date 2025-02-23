package com.FindTutor.FindTutor.DTO;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class TutorDTO {
    private int id;
    private int userID;
    private String fullname;
    private boolean gender; // true: Female, false: Male
    private Date dateOfBirth;
    private String address;
    private String qualification;
    private int experience;
    private String bio;
    private int status;
    private List<String> subjects; // Danh sách môn học

    // Constructor mặc định
    public TutorDTO() {}

    public TutorDTO(int id, int userID, String fullname, boolean gender, Date dateOfBirth, String address, String qualification, int experience, String bio, int status) {
        this.id = id;
        this.userID = userID;
        this.fullname = fullname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.qualification = qualification;
        this.experience = experience;
        this.bio = bio;
        this.status = status;
    }

    // Constructor với tham số
    public TutorDTO(int id, int userID, String fullname, boolean gender, Date dateOfBirth, String address, String qualification, int experience, String bio, int status, List<String> subjects) {
        this.id = id;
        this.userID = userID;
        this.fullname = fullname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.qualification = qualification;
        this.experience = experience;
        this.bio = bio;
        this.status = status;
        this.subjects = subjects;
    }
    
}
