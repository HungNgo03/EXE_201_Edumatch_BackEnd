package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.Entity.TutorAvailability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TutorAvailabilityRepository extends JpaRepository<TutorAvailability, Integer> {
    @Query("select t from TutorAvailability t where t.TutorID = :tutorId")
    List<TutorAvailability> getTutorAvailabilitiesByTutorID(int tutorId);
}
