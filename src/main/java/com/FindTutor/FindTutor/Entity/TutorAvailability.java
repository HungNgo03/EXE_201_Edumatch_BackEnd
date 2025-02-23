package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Data
@Entity
@Getter
@Setter
@Table(name = "TutorAvailability")
public class TutorAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    private int TutorID;
    private int DayOfWeek;
    private String StartTime;
    private String EndTime;
    private int Status;
}
