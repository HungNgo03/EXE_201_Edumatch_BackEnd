
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
    private String email;
    private String username;
    private String role;
    private String image;
    private List<String> subjects; // Danh sách môn học

    // Constructor mặc định
    public TutorDTO() {}

    public TutorDTO(String role, String username, String email, int status, String bio, int experience, String qualification, String address, Date dateOfBirth, boolean gender, String fullname, int userID, int id) {
        this.role = role;
        this.username = username;
        this.email = email;
        this.status = status;
        this.bio = bio;
        this.experience = experience;
        this.qualification = qualification;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.fullname = fullname;
        this.userID = userID;
        this.id = id;
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
    public TutorDTO(int id, int userID, String fullname, boolean gender, Date dateOfBirth,
                    String address, String qualification, int experience, String bio, int status,
                    String image, List<String> subjects) {
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
        this.image = image;
        this.subjects = subjects;
    }
    
}
