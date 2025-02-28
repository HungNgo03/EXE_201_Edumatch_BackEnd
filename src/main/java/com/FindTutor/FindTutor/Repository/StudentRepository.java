package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.Entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Integer> {
    Optional<Students> findByUserID(int UserID);
    Students getStudentsByUserID(int userId);

    @Query(value = "SELECT s.Date, s.StartTime, s.EndTime, c.ClassName, sub.subjectname " +
            "FROM Schedules s " +
            "JOIN Classes c ON s.ClassID = c.ID " +
            "JOIN ClassStudents cs ON c.ID = cs.ClassID " +
            "JOIN Students st ON cs.StudentID = st.ID " +
            "JOIN Subjects sub ON c.SubjectID = sub.ID " +
            "WHERE st.UserID = :studentId", nativeQuery = true)
    List<Object[]> getStudentSchedule(@Param("studentId") int studentId);
}
