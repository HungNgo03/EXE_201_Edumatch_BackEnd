
package com.FindTutor.FindTutor.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
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

    public SubjectDTO(int ID, String subjectname) {
        this.ID = ID;
        this.subjectname = subjectname;
    }

    public SubjectDTO() {
    }

}
