package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.ClassDetailsDTO;
import com.FindTutor.FindTutor.DTO.ClassListDTO;
import com.FindTutor.FindTutor.Entity.Classes;
import com.FindTutor.FindTutor.Entity.Students;

import java.util.List;

public interface IClassService {
    Classes registerClass(Classes classes);
    List<ClassListDTO> getClassListByUserId(int userId);
    ClassDetailsDTO getClassByClassName(String className);
}
