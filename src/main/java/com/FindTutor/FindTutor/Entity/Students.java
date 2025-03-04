package com.FindTutor.FindTutor.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "ClassStudents",
            joinColumns = @JoinColumn(name = "StudentID"),
            inverseJoinColumns = @JoinColumn(name = "ClassID")
    )
    private List<Classes> classes;
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
