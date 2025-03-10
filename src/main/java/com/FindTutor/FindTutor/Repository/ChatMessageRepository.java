package com.FindTutor.FindTutor.Repository;

import com.FindTutor.FindTutor.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT m FROM ChatMessage m WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender) ORDER BY m.timestamp ASC")
    List<ChatMessage> findChatHistory(@Param("sender") String sender, @Param("receiver") String receiver);

    @Query("SELECT DISTINCT COALESCE(CASE WHEN m.sender = 'admin' THEN m.receiver ELSE m.sender END, '') " +
            "FROM ChatMessage m " +
            "WHERE (m.sender = 'admin' OR m.receiver = 'admin') " +
            "AND m.receiver IS NOT NULL AND m.sender IS NOT NULL")
    List<String> findChattedUsersForAdmin();

}
