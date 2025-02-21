package com.FindTutor.FindTutor.Controller;


import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.ITutorService;
import com.FindTutor.FindTutor.dto.TutorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tutor")
public class TutorController {
    @Autowired
    private ITutorService tutorService;

    // Lấy tất cả gia sư với các tham số lọc
    @GetMapping("/getAllTutors")
    public Response<List<TutorDTO>> getAllTutors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String subject
    ) {
        List<TutorDTO> tutors = tutorService.getAllTutors(name, subject);
        return new Response<>(EHttpStatus.OK, tutors);
    }

}
