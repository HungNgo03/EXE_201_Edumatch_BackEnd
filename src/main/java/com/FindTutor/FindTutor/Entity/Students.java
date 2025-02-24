package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "Students")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column(name = "UserID", nullable = false, unique = true)
    private int userID;

    @Column(name = "ParentName")  // Thêm annotation này để tránh lỗi
    private String ParentName;

    @Column(name = "Grade")
    private String Grade;

    @Column(name = "Address")
    private String Address;

    @Column(name = "Notes")
    private String Notes;

    public Students() {
    }

    public Students(int ID, int userID, String parentName, String grade, String address, String notes) {
        this.ID = ID;
        this.userID = userID;
        ParentName = parentName;
        Grade = grade;
        Address = address;
        Notes = notes;
    }
}
