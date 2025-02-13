package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String Username;
    private String PasswordHash;
    private String FullName;
    @Email
    private String Email;
    private String PhoneNumber;
    private String Role;
    private Date CreatedAt;

    public Users() {
    }

    public Users(int ID, String username, String passwordHash, String fullName, String email, String phoneNumber, String role, Date createdAt) {
        this.ID = ID;
        Username = username;
        PasswordHash = passwordHash;
        FullName = fullName;
        Email = email;
        PhoneNumber = phoneNumber;
        Role = role;
        CreatedAt = createdAt;
    }
}
