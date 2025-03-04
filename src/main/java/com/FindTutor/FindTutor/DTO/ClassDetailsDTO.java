package com.FindTutor.FindTutor.DTO;

import com.FindTutor.FindTutor.Entity.Students;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ClassDetailsDTO {
    private String tutorName;
    private List<String> students;
    private int maxStudent;
    private Date startDate;

    public ClassDetailsDTO(String tutorName, List<String> studentsName, int maxStudent, Date startDate) {
        this.tutorName = tutorName;
        this.students = studentsName;
        this.maxStudent = maxStudent;
        this.startDate = startDate;
    }

    public ClassDetailsDTO(String tutorName, int maxStudent, Date startDate) {
        this.tutorName = tutorName;
        this.maxStudent = maxStudent;
        this.startDate = startDate;
    }
}
