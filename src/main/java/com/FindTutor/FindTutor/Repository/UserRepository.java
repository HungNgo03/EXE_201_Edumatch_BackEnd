package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.DTO.AdminDTO;
import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    Users findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Users> findById(int id);
    @Query("SELECT new com.FindTutor.FindTutor.DTO.AdminDTO(u.ID, u.username, u.fullname, u.email, u.phoneNumber, u.Role, u.CreatedAt) FROM Users u where u.Role = 'Student'")
    List<AdminDTO> findAllUser();
}
