package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.Entity.Subjects;
import com.FindTutor.FindTutor.DTO.SubjectDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subjects, Integer> {
    @Query("SELECT new com.FindTutor.FindTutor.DTO.SubjectDTO(s.ID, s.subjectname, s.Description) FROM Subjects s")

    List<SubjectDTO> getAllSubjects();
    Subjects getSubjectsBySubjectname(String subjectName);
}

