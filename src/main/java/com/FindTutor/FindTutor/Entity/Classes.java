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
@Table(name = "Classes")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "TutorID", nullable = false)
    private Tutors tutor;

    @ManyToOne
    @JoinColumn(name = "StudentID", nullable = false)
    private Students student;

    @ManyToOne
    @JoinColumn(name = "SubjectID", nullable = false)
    private Subjects subject;

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