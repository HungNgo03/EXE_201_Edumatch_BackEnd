package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.Entity.Subjects;
import com.FindTutor.FindTutor.Repository.SubjectRepository;
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.SubjectService;
import com.FindTutor.FindTutor.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController  // Đảm bảo rằng lớp này là một Spring Bean
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/getAllSubjects")
    public Response<List<SubjectDTO>> getAllSubjects() {
        List<SubjectDTO> subjects = subjectService.getAllSubjects();
        return new Response<>(EHttpStatus.OK, subjects);
    }
}
