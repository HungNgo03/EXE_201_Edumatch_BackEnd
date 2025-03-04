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
@Table(name = "Schedules")
public class Schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClassID", referencedColumnName = "ID", nullable = false)
    private Classes classes;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    public Schedules() {
    }
}