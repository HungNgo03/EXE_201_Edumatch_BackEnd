package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.dto.TutorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TutorRepository extends JpaRepository<Tutors, Integer> {
    // Lấy tất cả gia sư với thông tin người dùng
    @Query(value = "SELECT t.ID, t.UserID, u.fullname, t.Gender, t.date_of_birth, " +
            "t.Address, t.Qualification, t.Experience, t.Bio, t.Status, " +
            "STRING_AGG(s.subjectname, ',') AS subjects " +
            "FROM Tutors t " +
            "JOIN Users u ON t.UserID = u.ID " +
            "LEFT JOIN TutorSubjects ts ON t.ID = ts.TutorID " +
            "LEFT JOIN Subjects s ON ts.SubjectID = s.ID " +
            "WHERE (:name IS NULL OR u.fullname LIKE %:name%) " +
            "AND (:subject IS NULL OR s.subjectname LIKE %:subject%) " +
            "GROUP BY t.ID, t.UserID, u.fullname, t.Gender, t.date_of_birth, " +
            "t.Address, t.Qualification, t.Experience, t.Bio, t.Status",
            nativeQuery = true)
    List<Object[]> getTutorsWithFilters(@Param("name") String name,
                                        @Param("subject") String subject);


}
