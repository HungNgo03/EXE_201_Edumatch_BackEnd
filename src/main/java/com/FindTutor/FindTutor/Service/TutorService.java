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
            int exp = (int) row[7];  // Kinh nghiệm
            String bio = (String) row[8];
            int status = (int) row[9];
            String subjectsString = (String) row[10];

            List<String> subjects = Arrays.asList(subjectsString.split(","));

            TutorDTO tutor = new TutorDTO(id, userID, fullname, gender, dateOfBirth, address, qualification, exp, bio, status, subjects);
            tutors.add(tutor);
        }

        return tutors;
    }



    @Transactional

    @Override
    public TutorDetailDTO getTutorDetail(int tutorId) {
        Object result = tutorRepository.getTutorDetailById(tutorId);

        if (result == null) {
            return null; // Trả về null nếu không có dữ liệu
        }

        // Ép kiểu chính xác: result là một Object[], nên ta cần lấy từng phần tử
        Object[] row = (Object[]) result;

        int id = ((Number) row[0]).intValue();
        String fullname = (String) row[1];
        String email = (String) row[2];
        String phoneNumber = (String) row[3];

        boolean gender;
        if (row[4] instanceof Boolean) {
            gender = (Boolean) row[4];
        } else {
            gender = ((Number) row[4]).intValue() == 1;
        }

        Date dateOfBirth = (Date) row[5];
        String address = (String) row[6];
        String qualification = (String) row[7];
        int experience = ((Number) row[8]).intValue();
        String bio = (String) row[9];
        int status = ((Number) row[10]).intValue();
        String subjectsString = (String) row[11];

        // Kiểm tra NULL trước khi dùng
        List<String> subjects = subjectsString != null ? Arrays.asList(subjectsString.split(",")) : new ArrayList<>();

        // Lấy lịch giảng dạy
        List<Object[]> scheduleResults = tutorRepository.getTutorSchedule(tutorId);
        List<ScheduleDTO> schedules = new ArrayList<>();

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); // Format chuẩn

        for (Object[] scheduleRow : scheduleResults) {
            Date scheduleDate = (Date) scheduleRow[0];

            // Chuyển đổi Time sang String
            String startTime = (scheduleRow[1] instanceof Time) ? timeFormat.format((Time) scheduleRow[1]) : (String) scheduleRow[1];
            String endTime = (scheduleRow[2] instanceof Time) ? timeFormat.format((Time) scheduleRow[2]) : (String) scheduleRow[2];

            schedules.add(new ScheduleDTO(scheduleDate, startTime, endTime));
        }

        return new TutorDetailDTO(id, fullname, email, phoneNumber, gender, dateOfBirth, address, qualification, experience, bio, status, subjects, schedules);
    }

}
