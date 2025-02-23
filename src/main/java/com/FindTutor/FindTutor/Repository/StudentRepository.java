package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.Entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Integer> {
    Optional<Students> findByUserID(int UserID);
}
