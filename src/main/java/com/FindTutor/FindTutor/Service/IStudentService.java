package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.ScheduleDTO;

import java.util.List;

public interface IStudentService {
    List<ScheduleDTO> getStudentSchedule(int studentId);
}
