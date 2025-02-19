package com.FindTutor.FindTutor.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "Subjects")
public class Subjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String subjectname;
    private String Description;

    public Subjects() {
    }

    public Subjects(int ID, String subjectName, String description) {
        this.ID = ID;
        subjectname = subjectName;
        Description = description;
    }
}
