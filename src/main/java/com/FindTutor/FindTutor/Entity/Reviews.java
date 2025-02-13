package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "Reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int StudentID;
    private int TutorID;
    private int Rating;
    private String Comment;
    private Date CreatedAt;

    public Reviews() {
    }

    public Reviews(int ID, int studentID, int tutorID, int rating, String comment, Date createdAt) {
        this.ID = ID;
        StudentID = studentID;
        TutorID = tutorID;
        Rating = rating;
        Comment = comment;
        CreatedAt = createdAt;
    }
}
