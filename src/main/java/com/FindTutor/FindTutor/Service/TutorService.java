package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Repository.TutorRepository;

import com.FindTutor.FindTutor.DTO.TutorDTO;

import com.FindTutor.FindTutor.DTO.ScheduleDTO;
import com.FindTutor.FindTutor.DTO.TutorDetailDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.SimpleDateFormat;

import java.util.List;


@Service
public class TutorService implements ITutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Transactional
    @Override
    public List<TutorDTO> getAllTutors(String name, String subject) {
        List<Object[]> results = tutorRepository.getTutorsWithFilters(name, subject);
        List<TutorDTO> tutors = new ArrayList<>();

        for (Object[] row : results) {
            int id = (int) row[0];
            int userID = (int) row[1];
            String fullname = (String) row[2];
            boolean gender = (boolean) row[3];
            Date dateOfBirth = (Date) row[4];
            String address = (String) row[5];
            String qualification = (String) row[6];
            int experience = (int) row[7];
            String bio = (String) row[8];
            int status = (int) row[9];
            String image = (String) row[10];
            String subjectsString = (String) row[11];
            List<String> subjects = subjectsString.isEmpty() ? new ArrayList<>() : Arrays.asList(subjectsString.split(","));

            TutorDTO tutor = new TutorDTO(id, userID, fullname, gender, dateOfBirth, address, qualification, experience, bio, status, image, subjects);
            tutors.add(tutor);
        }

        return tutors;
    }



    @Transactional
    @Override
    public List<ScheduleDTO> getTutorSchedule(int tutorId) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        List<Object[]> scheduleResults = tutorRepository.getTutorSchedule(tutorId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

        for (Object[] scheduleRow : scheduleResults) {
            java.sql.Date scheduleDate = (Date) scheduleRow[0];
            String startTime = (scheduleRow[1] instanceof Time) ? timeFormat.format((Time) scheduleRow[1]) : (String) scheduleRow[1];
            String endTime = (scheduleRow[2] instanceof Time) ? timeFormat.format((Time) scheduleRow[2]) : (String) scheduleRow[2];
            String className = (String) scheduleRow[3];
            String subjectName = (String) scheduleRow[4];

            scheduleDTOs.add(new ScheduleDTO(scheduleDate, startTime, endTime, className, subjectName));
        }
        return scheduleDTOs;
    }



}
