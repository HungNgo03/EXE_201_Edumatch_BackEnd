package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.Entity.Classes;
import com.FindTutor.FindTutor.Entity.Tutors;


import com.FindTutor.FindTutor.DTO.TutorRequest;
import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.Repository.TutorRepository;
import com.FindTutor.FindTutor.Repository.UserRepository;

import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.IClassService;
import com.FindTutor.FindTutor.Service.IScheduleService;
import com.FindTutor.FindTutor.Service.ITutorService;

import com.FindTutor.FindTutor.DTO.TutorDTO;

import com.FindTutor.FindTutor.DTO.TutorDetailDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Map;

import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tutor")
public class TutorController {
    @Autowired
    private ITutorService tutorService;


//    @Autowired
//    private IScheduleService IscheduleService;

    @Autowired
    private IClassService classService;

    // API lấy danh sách gia sư
//   @GetMapping("/getAll")
//    public Response<List<Tutors>> getAllTutors() {
//        return new Response<>(EHttpStatus.OK, tutorService.getAllTutors());
//    }



    // API lấy chi tiết gia sư theo ID
    @GetMapping("/getTutorDetail/{tutorId}")
    public Response<TutorDetailDTO> getTutorDetail(@PathVariable int tutorId) {
        TutorDetailDTO tutorDetail = tutorService.getTutorDetail(tutorId);

        if (tutorDetail == null) {
            return new Response<>(EHttpStatus.NOT_FOUND, "Tutor not found", null);
        }

        return new Response<>(EHttpStatus.OK, tutorDetail);
    }

    // API đăng ký học
    @PostMapping("/registerClass")
    public Response<Classes> registerClass(@RequestBody Classes classes) {
        return new Response<>(EHttpStatus.OK, classService.registerClass(classes));
    }

    private UserRepository usersRepository;

    @Autowired
    private TutorRepository tutorsRepository;

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
