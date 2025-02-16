package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.Entity.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedules, Integer>
{
    List<Schedules> findByTutor_Id(int tutorId);
}
