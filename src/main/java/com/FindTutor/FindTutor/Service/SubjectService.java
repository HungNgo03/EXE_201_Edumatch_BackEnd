package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Repository.SubjectRepository;
import com.FindTutor.FindTutor.DTO.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.getAllSubjects();  // Gọi repository để lấy môn học
    }
}
