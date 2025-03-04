package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.DTO.ScheduleDTO;
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
@RequestMapping("/api/tutor")
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


    @GetMapping("/getSchedule/{userId}")
    public List<ScheduleDTO> getTutorSchedule(@PathVariable int userId) {
        return tutorService.getTutorSchedule(userId);
    }





}






