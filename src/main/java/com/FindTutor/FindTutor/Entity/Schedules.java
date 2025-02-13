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
    private int Id;
    private int ClassID;
    private Date Date;
    private String StartTime;
    private String EndTime;

    public Schedules() {
    }

    public Schedules(int id, int classID, java.util.Date date, String startTime, String endTime) {
        Id = id;
        ClassID = classID;
        Date = date;
        StartTime = startTime;
        EndTime = endTime;
    }
}
