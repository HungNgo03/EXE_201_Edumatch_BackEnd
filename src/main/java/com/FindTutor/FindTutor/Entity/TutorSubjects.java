package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "TutorSubjects", schema = "dbo")
public class TutorSubjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int TutorID;
    private int SubjectID;

//    @ManyToOne
//    @JoinColumn(name = "TutorID", referencedColumnName = "ID", insertable = false, updatable = false)
//    private Tutors tutor;
//
//    @ManyToOne
//    @JoinColumn(name = "SubjectID", referencedColumnName = "ID", insertable = false, updatable = false)
//    private Subjects subject;

    public TutorSubjects() {
    }

    public TutorSubjects(int ID, int tutorID, int subjectID) {
        this.ID = ID;
        TutorID = tutorID;
        SubjectID = subjectID;
    }
}
