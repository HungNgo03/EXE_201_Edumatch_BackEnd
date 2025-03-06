package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.Entity.Classes;
import com.FindTutor.FindTutor.Entity.Students;
import com.FindTutor.FindTutor.Entity.Tutors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClassRepository extends JpaRepository<Classes, Integer> {
    @Query("select c from Classes c where c.tutor.ID = :tutorId")
    List<Classes> getClassesByTutorId(int tutorId);
    @Query("SELECT c FROM Classes c JOIN c.student s WHERE s.ID = :studentId")
    List<Classes> getClassesByStudentId(@Param("studentId") int studentId);
    Classes getClassesByClassName(String className);
    @Query("SELECT s FROM Students s JOIN s.classes c WHERE c.className = :className")
    List<Students> findStudentsByClassName(@Param("className") String className);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ClassStudents (ClassID, StudentID) VALUES (:classId, :studentId)", nativeQuery = true)
    void addStudentToClass(@Param("classId") int classId, @Param("studentId") int studentId);
}
