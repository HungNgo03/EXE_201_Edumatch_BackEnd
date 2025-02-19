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
    private String username;
    @Column(name = "password_hash")
    private String passwordHash;
    private String fullname;
    @Email
    private String email;
    @Column(name = "phone_number")
    private String PhoneNumber;
    private String Role;
    @Column(name = "created_at")
    private Date CreatedAt;

    public Users() {
    }

    public Users(int ID, String username, String passwordHash, String fullName, String email, String phoneNumber, String role, Date createdAt) {
        this.ID = ID;
        this.username = username;
        this.passwordHash = passwordHash;
        fullname = fullName;
        this.email = email;
        PhoneNumber = phoneNumber;
        Role = role;
        CreatedAt = createdAt;
    }
}
