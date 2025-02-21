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

    @Column(name = "UserID", nullable = false)
    private int UserID;

    @Column(name = "Gender", nullable = false)
    private Boolean Gender; // true: Female, false: Male

    @Column(name = "DateOfBirth", nullable = false)
    private Date DateOfBirth;

    @Column(name = "Address", nullable = false)
    private String Address;

    @Column(name = "Qualification", nullable = false)
    private String Qualification;

    @Column(name = "Experience", nullable = false)
    private int Experience;

    @Column(name = "Bio")
    private String Bio;

    @Column(name = "Status")
    private int Status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private Users user;

    // Quan hệ Many-to-Many với Subjects thông qua bảng liên kết TutorSubjects
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TutorSubjects",
            joinColumns = @JoinColumn(name = "TutorID"),
            inverseJoinColumns = @JoinColumn(name = "SubjectID")
    )
    private List<Subjects> subjects;

}