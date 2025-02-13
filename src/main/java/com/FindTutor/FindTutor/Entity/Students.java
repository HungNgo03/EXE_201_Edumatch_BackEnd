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
    private int UserID;
    private String ParentName;
    private String Grade;
    private String Address;
    private String Notes;

    public Students() {
    }

    public Students(int ID, int userID, String parentName, String grade, String address, String notes) {
        this.ID = ID;
        UserID = userID;
        ParentName = parentName;
        Grade = grade;
        Address = address;
        Notes = notes;
    }
}
