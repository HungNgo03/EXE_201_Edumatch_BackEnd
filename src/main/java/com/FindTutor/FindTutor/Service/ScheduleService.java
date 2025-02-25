package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Schedules;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScheduleService implements IScheduleService{
    @Override
    public List<Schedules> getSchedulesByTutorId(Integer tutorId) {
        return null;
    }
}
