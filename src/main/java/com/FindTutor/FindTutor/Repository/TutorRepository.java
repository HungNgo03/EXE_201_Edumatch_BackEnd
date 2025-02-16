package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.Entity.Tutors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends JpaRepository<Tutors, Integer> {
    List<Tutors> findByStatus(int status);

}
