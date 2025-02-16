package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Classes;
import com.FindTutor.FindTutor.Repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassService implements IClassService {
    @Autowired
    private ClassRepository classRepository;

    @Override
    public Classes registerClass(Classes classes) {
        return classRepository.save(classes);
    }
}
