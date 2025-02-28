package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.DTO.TutorDTO;
import com.FindTutor.FindTutor.Entity.Tutors;

import com.FindTutor.FindTutor.Entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutors, Integer> {
    @Query(value = "SELECT t.ID, t.UserID, u.fullname, t.Gender, t.DateOfBirth, " +

            "t.Address, t.Qualification, t.Experience, t.Bio, t.Status, " +
            "STRING_AGG(s.subjectname, ',') AS subjects " +
            "FROM Tutors t " +
            "JOIN Users u ON t.UserID = u.ID " +
            "LEFT JOIN TutorSubjects ts ON t.ID = ts.TutorID " +
            "LEFT JOIN Subjects s ON ts.SubjectID = s.ID " +
            "WHERE (:name IS NULL OR u.fullname LIKE %:name%) " +
            "AND (:subject IS NULL OR s.subjectname LIKE %:subject%) " +

            "GROUP BY t.ID, t.UserID, u.fullname, t.Gender, t.DateOfBirth, " +

            "t.Address, t.Qualification, t.Experience, t.Bio, t.Status",
            nativeQuery = true)
    List<Object[]> getTutorsWithFilters(@Param("name") String name,
                                        @Param("subject") String subject);

    Optional<Tutors> findByUserID(int UserID);

    @Query(value = "SELECT t.ID, u.fullname, u.email, u.phone_number, t.Gender, " +
            "t.DateOfBirth, t.Address, t.Qualification, t.Experience, t.Bio, t.Status, " +
            "STRING_AGG(s.subjectname, ',') AS subjects ,t.money_per_slot " +
            "FROM Tutors t " +
            "JOIN Users u ON t.UserID = u.ID " +
            "LEFT JOIN TutorSubjects ts ON t.ID = ts.TutorID " +
            "LEFT JOIN Subjects s ON ts.SubjectID = s.ID " +
            "WHERE t.ID = :tutorId " +
            "GROUP BY t.ID, u.fullname, u.email, u.phone_number, t.Gender, " +
            "t.DateOfBirth, t.Address, t.Qualification, t.Experience, t.Bio, t.Status,t.money_per_slot",
            nativeQuery = true)
    Object getTutorDetailById(@Param("tutorId") int tutorId);

    @Query(value = "SELECT s.Date, s.StartTime, s.EndTime " +
            "FROM Schedules s " +
            "JOIN Classes c ON s.ClassID = c.ID " +
            "WHERE c.TutorID = :tutorId", nativeQuery = true)
    List<Object[]> getTutorSchedule(@Param("tutorId") int tutorId);
    @Query("SELECT new com.FindTutor.FindTutor.DTO.TutorDTO(u.Role,u.username,u.email, t.Status, t.Bio, t.Experience, t.Qualification, t.Address, t.DateOfBirth, t.Gender, u.fullname, t.userID,t.ID) " +
            "FROM Tutors t JOIN t.user u")
    List<TutorDTO> findAllTutor();
    
}
