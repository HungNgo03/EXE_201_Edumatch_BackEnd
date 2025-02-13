package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "Tutors")
public class Tutors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int UserID;
    private boolean Gender;
    private Date DateOfBirth;
    private String Address;
    private String Qualification;
    private String Experience;
    private String Bio;
    private int Status;

    public Tutors() {
    }

    public Tutors(int ID, int userID, boolean gender, Date dateOfBirth, String address, String qualification, String experience, String bio, int status) {
        this.ID = ID;
        UserID = userID;
        Gender = gender;
        DateOfBirth = dateOfBirth;
        Address = address;
        Qualification = qualification;
        Experience = experience;
        Bio = bio;
        Status = status;
    }
}
