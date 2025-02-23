package com.FindTutor.FindTutor.DTO;

import lombok.Data;

@Data
public class SubjectDTO {
    private int ID;
    private String subjectname;
    private String description;

    // Constructor
    public SubjectDTO(int ID, String subjectname, String description) {
        this.ID = ID;
        this.subjectname = subjectname;
        this.description = description;
    }

    public SubjectDTO() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
