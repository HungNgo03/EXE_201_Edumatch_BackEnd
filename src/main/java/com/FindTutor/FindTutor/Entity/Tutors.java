package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@Table(name = "Tutors")
public class Tutors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

<<<<<<< Updated upstream
    @OneToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID", nullable = false)
=======
    @Column(name = "UserID", nullable = false, unique = true)
    private int userID;

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
>>>>>>> Stashed changes
    private Users user;

    @Column(nullable = false)
    private boolean gender;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String qualification;

    private int experience;
    private String bio;
    private int status;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    private List<Classes> classes;

    public Tutors() {
    }

    public Tutors(int id, Users user, boolean gender, Date dateOfBirth, String address, String qualification, int experience, String bio, int status, List<Classes> classes) {
        this.id = id;
        this.user = user;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.qualification = qualification;
        this.experience = experience;
        this.bio = bio;
        this.status = status;
        this.classes = classes;
    }
}
