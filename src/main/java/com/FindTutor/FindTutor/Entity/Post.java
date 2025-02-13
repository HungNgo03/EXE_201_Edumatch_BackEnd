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
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int UserID;
    private int SubjectID;
    private String Title;
    private String Content;
    private int Rating;
    private Date CreatedAt;
    private Date UpdatedAt;

    public Post() {
    }

    public Post(int ID, int userID, int subjectID, String title, String content, int rating, Date createdAt, Date updatedAt) {
        this.ID = ID;
        UserID = userID;
        SubjectID = subjectID;
        Title = title;
        Content = content;
        Rating = rating;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
    }
}
