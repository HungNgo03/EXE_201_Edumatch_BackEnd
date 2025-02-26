package com.FindTutor.FindTutor.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduleDTO {
    private Date date;
    private String startTime;
    private String endTime;
    private String className;
    private String subjectName;

    public ScheduleDTO() {}

    public ScheduleDTO(Date date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ScheduleDTO(Date date, String startTime, String endTime, String className, String subjectName) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.className = className;
        this.subjectName = subjectName;
    }
}
