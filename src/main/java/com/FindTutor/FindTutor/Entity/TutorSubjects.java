package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "TutorSubjects")
public class TutorSubjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int TutorID;
    private int SubjectID;

    public TutorSubjects() {
    }

    public TutorSubjects(int ID, int tutorID, int subjectID) {
        this.ID = ID;
        TutorID = tutorID;
        SubjectID = subjectID;
    }
}
