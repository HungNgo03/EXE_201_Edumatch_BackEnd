package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Repository.TutorRepository;
import com.FindTutor.FindTutor.dto.TutorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

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

}
