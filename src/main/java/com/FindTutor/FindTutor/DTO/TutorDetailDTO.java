package com.FindTutor.FindTutor.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TutorDetailDTO {
    private int id;
    private String fullname;
    private String email;
    private String phoneNumber;
    private boolean gender; // true: Female, false: Male
    private Date dateOfBirth;
    private String address;
    private String qualification;
    private int experience;
    private String bio;
    private int status;
    private List<String> subjects; // Danh sách môn học
    private List<ScheduleDTO> schedule; // Lịch giảng dạy

    public TutorDetailDTO() {}

    public TutorDetailDTO(int id, String fullname, String email, String phoneNumber, boolean gender, Date dateOfBirth,
                          String address, String qualification, int experience, String bio, int status,
                          List<String> subjects, List<ScheduleDTO> schedule) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.qualification = qualification;
        this.experience = experience;
        this.bio = bio;
        this.status = status;
        this.subjects = subjects;
        this.schedule = schedule;
    }
}
