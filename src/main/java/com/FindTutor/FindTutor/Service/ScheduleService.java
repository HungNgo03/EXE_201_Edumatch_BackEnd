package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Schedules;
import com.FindTutor.FindTutor.Repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService implements IScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Schedules> getSchedulesByTutorId(Integer tutorId) {
        return scheduleRepository.findByTutor_Id(tutorId);
    }
}
