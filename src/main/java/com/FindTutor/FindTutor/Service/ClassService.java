package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.ClassDetailsDTO;
import com.FindTutor.FindTutor.DTO.ClassListDTO;
import com.FindTutor.FindTutor.Entity.Classes;
import com.FindTutor.FindTutor.Entity.Students;
import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.Repository.ClassRepository;
import com.FindTutor.FindTutor.Repository.StudentRepository;
import com.FindTutor.FindTutor.Repository.TutorRepository;
import com.FindTutor.FindTutor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassService implements IClassService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public Classes registerClass(Classes classes) {
        return classRepository.save(classes);
    }

    @Override
    public List<ClassListDTO> getClassListByUserId(int userId) {
        Users user = userRepository.getUsersByID(userId);
        List<ClassListDTO> dto = new ArrayList<>();
        if(user.getRole().equals("Tutor")){
            Tutors tutor = tutorRepository.getTutorsByUserID(userId);
            List<Classes> classes= classRepository.getClassesByTutorId(tutor.getID());
            for(Classes cls:classes){
                ClassListDTO listDTO = new ClassListDTO(cls.getClassName(),cls.getTutor().getUser().getFullname());
                dto.add(listDTO);
            }
            return dto;
        }
        if(user.getRole().equals("Student")){
            Students std = studentRepository.getStudentsByUserID(userId);
            List<Classes> classes = classRepository.getClassesByStudentId(std.getID());
            for (Classes cls : classes) {
                ClassListDTO listDTO = new ClassListDTO(cls.getClassName(), cls.getTutor().getUser().getFullname());
                dto.add(listDTO);
            }
            return dto;
        }
        if(user.getRole().equals("Admin")){
            List<Classes> classes = classRepository.getAllClasses();
            for (Classes cls : classes) {
                ClassListDTO listDTO = new ClassListDTO(cls.getClassName(), cls.getTutor().getUser().getFullname());
                dto.add(listDTO);
            }
            return dto;
        }
        return null;
    }

    @Override
    public ClassDetailsDTO getClassByClassName(String className) {
        Classes cls =  classRepository.getClassesByClassName(className);
        List<Students> studentsList= classRepository.findStudentsByClassName(className);
        List<String> studentName= new ArrayList<>();
        for(Students std:studentsList){
            Users user = userRepository.getUsersByID(std.getUserID());
            studentName.add(user.getFullname());
        }
        return new ClassDetailsDTO(
                cls.getTutor().getUser().getFullname(),
                studentName,
                cls.getMaxStudent(),
                cls.getStartDate()
        );
    }
}
