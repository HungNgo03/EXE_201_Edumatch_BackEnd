package com.FindTutor.FindTutor.DTO;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PostDTO {
    private int id;
    private int userID;
    private String fullname; // Thêm tên người dùng
    private int subjectID;
    private String subjectname; // Thêm tên môn học vào bài viết
    private String title;
    private String content;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostDTO(int id, int userID, String fullname, int subjectID, String subjectname, String title, String content, int rating, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userID = userID;
        this.fullname = fullname;
        this.subjectID = subjectID;
        this.subjectname = subjectname;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
