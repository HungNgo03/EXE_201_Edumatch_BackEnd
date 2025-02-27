package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "ClassRegistrations")

public class ClassRegistrations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "StudentID")
    private int studentId;
    @Column(name = "ClassID",nullable = true)
    private Integer classId;
    @Column(name = "Status")
    private String status ;
    @Column(name = "SubjectName")
    private String subject;

    @Column(name = "Grade")
    private String grade;
    @Column(name = "paymentStatus")
    private int paymentStatus;
    @Column(name = "tutor")
    private int tutorId;
    @Column(name = "PreferredSchedule")
    private String preferredSchedule;
    @Column(name = "RegisteredAt", updatable = false)
    private LocalDateTime registeredAt = LocalDateTime.now();

    public ClassRegistrations(int studentId, Integer classId, String status, String subject, String grade, String preferredSchedule,int paymentStatus,int tutorId) {
        this.studentId = studentId;
        this.classId = classId;
        this.status = status;
        this.subject = subject;
        this.grade = grade;
        this.preferredSchedule = preferredSchedule;
        this.paymentStatus = paymentStatus;
        this.tutorId = tutorId;
    }

    public ClassRegistrations() {

    }
}
