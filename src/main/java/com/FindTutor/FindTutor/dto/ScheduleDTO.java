package com.FindTutor.FindTutor.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduleDTO {
    private Date date;
    private String startTime;
    private String endTime;

    public ScheduleDTO() {}

    public ScheduleDTO(Date date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
