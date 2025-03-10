package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.DTO.ClassRegistrationDetailsDTO;
import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClassRegistrationRepository extends JpaRepository<ClassRegistrations,Long> {
    ClassRegistrations getClassRegistrationsByID(int Id);
    List<ClassRegistrations> getClassRegistrationsByStudentId(int studentId);
    List<ClassRegistrations> getClassRegistrationsByTutorId(int tutorId);
    @Query(value = "select c from ClassRegistrations c")
    List<ClassRegistrations> getAllClassRegistration();
}
