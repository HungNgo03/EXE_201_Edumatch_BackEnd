package com.FindTutor.FindTutor.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Date;

@Data
public class AdminDTO {
    private int ID;
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String Role;
    private Date CreatedAt;

    public AdminDTO() {
    }

    public AdminDTO(int ID, String username, String fullname, String email, String phoneNumber, String role, Date createdAt) {
        this.ID = ID;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        Role = role;
        CreatedAt = createdAt;
    }
}
