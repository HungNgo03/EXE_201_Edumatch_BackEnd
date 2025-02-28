package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.DTO.AdminDTO;
import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    Users findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Users> findById(int id);

    @Query(value = "SELECT CASE WHEN EXISTS(SELECT 1 FROM Users WHERE username = :username AND id != :id)THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END", nativeQuery = true)
    boolean existsByUsername2(@Param("username") String username, @Param("id") int id);

    @Query(value = "SELECT CASE WHEN EXISTS(SELECT 1 FROM Users WHERE Email = :Email AND id != :id)THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END", nativeQuery = true)
    boolean existsByEmail2(@Param("Email")String email, @Param("id")int id);
    @Query(value = "SELECT CASE WHEN EXISTS(SELECT 1 FROM Users WHERE phone_number = :phone_number AND id != :id)THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END", nativeQuery = true)
    boolean existsByPhoneNumber2(@Param("phone_number")String phoneNumber, @Param("id")int id);
    Users getUsersByID(int Id);
    @Query("SELECT new com.FindTutor.FindTutor.DTO.AdminDTO(u.ID, u.username, u.fullname, u.email, u.phoneNumber, u.Role, u.CreatedAt) FROM Users u where u.Role = 'Student'")
    List<AdminDTO> findAllUser();
}
