package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "Messages")
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int SenderID;
    private int ReceiverID;
    private String Message;
    private Date SentAt;

    public Messages() {
    }

    public Messages(int ID, int senderID, int receiverID, String message, Date sentAt) {
        this.ID = ID;
        SenderID = senderID;
        ReceiverID = receiverID;
        Message = message;
        SentAt = sentAt;
    }
}
