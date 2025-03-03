package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.Entity.TutorAvailability;
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.ITutorAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tutor/avalability")
public class TutorAvalabilityController {
    @Autowired
    private ITutorAvailabilityService availabilityService;
    @GetMapping("getAvalabilityByTutorId/{tutorId}")
    public Response<List<TutorAvailability>> getAvailabilityByTutorId(@PathVariable int tutorId){
        return new Response<>(EHttpStatus.OK, availabilityService.getAvailability(tutorId));
    }

}
