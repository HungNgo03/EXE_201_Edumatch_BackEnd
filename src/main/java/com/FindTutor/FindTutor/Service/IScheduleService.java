package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Schedules;

import java.util.List;

public interface IScheduleService {
    List<Schedules> getSchedulesByTutorId(Integer tutorId);
}
