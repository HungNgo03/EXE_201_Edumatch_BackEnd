package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // Ensure this import is present
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "Classes")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int TutorID;
    private int StudentID;
    private int SubjectID;
    private Date StartDate;
    private Date EndDate;
    private String Status;

    public Classes() {
    }

    public Classes(int id, int tutorId, int studentId, int subjectId, Date startDate, Date endDate, String status) {
        ID = id;
        TutorID = tutorId;
        StudentID = studentId;
        SubjectID = subjectId;
        StartDate = startDate;
        EndDate = endDate;
        Status = status;
    }
}