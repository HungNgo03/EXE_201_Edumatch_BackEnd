package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    

    @Query("SELECT DISTINCT m.receiver FROM ChatMessage m " +
            "JOIN Users u ON m.receiver = u.username " +
            "WHERE m.sender = :username AND u.Role = 'Tutor' " +
            "UNION " +
            "SELECT DISTINCT m.sender FROM ChatMessage m " +
            "JOIN Users u ON m.sender = u.username " +
            "WHERE m.receiver = :username AND u.Role = 'Tutor'")
    List<String> findChattedTutorsForStudent(String username);

    @Query("SELECT DISTINCT m.receiver FROM ChatMessage m " +
            "JOIN Users u ON m.receiver = u.username " +
            "WHERE m.sender = :username AND u.Role = 'Student' " +
            "UNION " +
            "SELECT DISTINCT m.sender FROM ChatMessage m " +
            "JOIN Users u ON m.sender = u.username " +
            "WHERE m.receiver = :username AND u.Role = 'Student'")
    List<String> findChattedStudentsForTutor(String username);

    @Query("SELECT DISTINCT m.sender FROM ChatMessage m " +
            "WHERE m.receiver = 'admin' AND m.sender != 'admin' " +
            "UNION " +
            "SELECT DISTINCT m.receiver FROM ChatMessage m " +
            "WHERE m.sender = 'admin' AND m.receiver != 'admin'")
    List<String> findAllChattedUsers();
    @Query("SELECT m FROM ChatMessage m WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender) ORDER BY m.timestamp")
    List<ChatMessage> findChatHistory(String sender, String receiver);

}
