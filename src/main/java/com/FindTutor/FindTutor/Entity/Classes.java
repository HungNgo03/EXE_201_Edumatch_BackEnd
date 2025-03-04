package com.FindTutor.FindTutor.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "Classes")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ClassName")
    private String className;
    @Column(name = "MaxStudents")
    private int maxStudent;
    @ManyToOne
    @JoinColumn(name = "TutorID", nullable = false)
    private Tutors tutor;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "ClassStudents",
            joinColumns = @JoinColumn(name = "ClassID"),
            inverseJoinColumns = @JoinColumn(name = "StudentID")
    )
    private List<Students> student;

    @Column(nullable = false)
    private Date startDate;

    private Date endDate;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "classes", cascade = CascadeType.ALL)
    private List<Schedules> schedules;

    public Classes() {
    }
}