package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.DTO.PostDTO;
import com.FindTutor.FindTutor.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT new com.FindTutor.FindTutor.DTO.PostDTO(p.ID, p.UserID, u.fullname, p.SubjectID, s.subjectname, p.Title, p.Content, p.Rating, p.CreatedAt, p.UpdatedAt) " +
            "FROM Post p " +
            "JOIN Users u ON p.UserID = u.ID " +
            "JOIN Subjects s ON p.SubjectID = s.ID")
    List<PostDTO> getAllPostWithUserAndSubject();
    @Query("SELECT new com.FindTutor.FindTutor.DTO.PostDTO(p.ID, p.UserID, u.fullname, p.SubjectID, s.subjectname, p.Title, p.Content, p.Rating, p.CreatedAt, p.UpdatedAt) " +
            "FROM Post p " +
            "JOIN Users u ON p.UserID = u.ID " +
            "JOIN Subjects s ON s.ID = p.SubjectID " +
            "WHERE u.username = :username")
    List<PostDTO> findPostsByUsername(String username);

    @Query("SELECT new com.FindTutor.FindTutor.DTO.PostDTO(p.ID, p.UserID, u.fullname, p.SubjectID, s.subjectname, p.Title, p.Content, p.Rating, p.CreatedAt, p.UpdatedAt) " +
            "FROM Post p " +
            "JOIN Subjects s ON p.SubjectID = s.ID " +
            "JOIN Users u ON p.UserID = u.ID " +
            "WHERE s.subjectname = :subjectname")
    List<PostDTO> findPostsBySubject(String subjectname);
    @Query("SELECT DISTINCT s.subjectname FROM Post p " +
            "JOIN Subjects s ON p.SubjectID = s.ID")
    List<String> getAllSubject();
    @Query("SELECT distinct new com.FindTutor.FindTutor.DTO.PostDTO(p.ID, p.UserID, u.fullname, p.SubjectID, s.subjectname, p.Title, p.Content, p.Rating, p.CreatedAt, p.UpdatedAt) " +
            "FROM Post p " +
            "JOIN Users u ON p.UserID = u.ID " +
            "JOIN Subjects s ON p.SubjectID = s.ID " +
            "WHERE s.subjectname = :subjectname AND u.username = :username")
    List<PostDTO> findPostsBySubjectAndUsername(String subjectname, String username);
}
