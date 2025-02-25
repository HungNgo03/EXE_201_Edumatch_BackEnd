package com.FindTutor.FindTutor.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.security.auth.Subject;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "Tutors")
public class Tutors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;



    @Column(name = "Gender", nullable = false)
    private Boolean Gender; // true: Female, false: Male

    @Column(name = "DateOfBirth", nullable = false)
    private Date DateOfBirth;

    @Column(name = "Address", nullable = false)
    private String Address;


    @Column(name = "UserID", nullable = false, unique = true)
    private int userID;

    @Column(name = "Qualification", nullable = false)
    private String Qualification;

    @Column(name = "Experience", nullable = false)
    private int Experience;

    @Column(name = "Bio")
    private String Bio;
    @Column(name = "bank_image")
    private String bank_image;
    @Column(name = "Status")
    private int Status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", insertable = false, updatable = false)

    private Users user;

}