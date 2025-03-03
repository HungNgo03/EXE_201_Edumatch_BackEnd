package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;
    @GetMapping("/getAllStudents")
    public Response<?> getAllUsers() {
        return new Response<>(EHttpStatus.OK, adminService.getAllUsers());
    }
    @GetMapping("/getAllTutors")
    public Response<?> getAllTutors() {
        return new Response<>(EHttpStatus.OK, adminService.getAllTutors());
    }
    @PutMapping("/tutors/{id}")
    public Response<?> updateTutor(@PathVariable int id, @RequestBody Tutors tutorDetails) {
        try {
            Tutors updatedTutor = adminService.updateTutor(id, tutorDetails);
            return new Response<>(EHttpStatus.OK, updatedTutor);
        } catch (Exception e) {
            return new Response<>(EHttpStatus.NOT_FOUND, "Tutor not found with id: " + id);
        }
    }
}
