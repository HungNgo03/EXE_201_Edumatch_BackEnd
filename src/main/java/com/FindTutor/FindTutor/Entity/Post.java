package com.FindTutor.FindTutor.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @JsonProperty("UserID")
    private int UserID;
    @JsonProperty("SubjectID")
    private int SubjectID;
    @JsonProperty("Title")
    private String Title;
    @JsonProperty("Content")
    private String Content;
    @JsonProperty("Rating")
    private int Rating;
    @Column(name = "created_at")
    private LocalDateTime CreatedAt;
    @Column(name = "updated_at")
    private LocalDateTime UpdatedAt;

    @PrePersist
    protected void onCreate() {
        CreatedAt = LocalDateTime.now();
        UpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        UpdatedAt = LocalDateTime.now();
    }
    public Post() {
    }

    public Post(int ID, int userID, int subjectID, String title, String content, int rating, LocalDateTime  createdAt, LocalDateTime  updatedAt) {
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
