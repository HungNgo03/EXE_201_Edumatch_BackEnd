package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.ScheduleDTO;
import com.FindTutor.FindTutor.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<ScheduleDTO> getStudentSchedule(int studentId) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        List<Object[]> scheduleResults = studentRepository.getStudentSchedule(studentId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Object[] scheduleRow : scheduleResults) {
            java.sql.Date scheduleDate = (Date) scheduleRow[0];
            String startTime = (scheduleRow[1] instanceof Time) ? timeFormat.format((Time) scheduleRow[1]) : (String) scheduleRow[1];
            String endTime = (scheduleRow[2] instanceof Time) ? timeFormat.format((Time) scheduleRow[2]) : (String) scheduleRow[2];
            String className = (String) scheduleRow[3];
            String subjectName = (String) scheduleRow[4];
            scheduleDTOs.add(new ScheduleDTO(scheduleDate,startTime,endTime,className,subjectName));
        }
        return scheduleDTOs;
    }
}
