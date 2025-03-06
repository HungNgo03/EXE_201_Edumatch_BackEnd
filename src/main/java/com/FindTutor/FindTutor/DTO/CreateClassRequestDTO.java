package com.FindTutor.FindTutor.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class CreateClassRequestDTO {
    private int registerId;
    private Date startDate;
    private String preferedSchedule;
}
