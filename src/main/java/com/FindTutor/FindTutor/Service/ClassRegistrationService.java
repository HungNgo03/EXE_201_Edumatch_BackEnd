package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import com.FindTutor.FindTutor.Repository.ClassRegistrationRepository;
import com.FindTutor.FindTutor.DTO.ClassRegistrationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassRegistrationService implements IClassRegistrationService{
    @Autowired
    private ClassRegistrationRepository registrationRepository;
    @Override
    public void registerStudent(ClassRegistrationRequestDTO request) {
        if (request.getClassId() == null){
            request.setClassId(null);
        }
        ClassRegistrations classRegistrations = new ClassRegistrations(1, request.getClassId(), "Pending", request.getSubjectId(), request.getGrade(), request.getPreferredSchedule());
        registrationRepository.save(classRegistrations);
    }
}
