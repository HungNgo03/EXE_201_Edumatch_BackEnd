package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.DTO.*;
import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import com.FindTutor.FindTutor.Entity.Classes;
import com.FindTutor.FindTutor.Entity.Students;
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.IClassRegistrationService;
import com.FindTutor.FindTutor.Service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassRegistrationController {
    @Autowired
    private IClassRegistrationService classRegistrationService;
    @Autowired
    private IClassService classService;

    @PostMapping("/register")
    public Response<ClassRegistrations> registerStudent(@RequestBody ClassRegistrationRequestDTO request) {
            return new Response<>(EHttpStatus.OK, classRegistrationService.registerStudent(request));
    }

    @GetMapping("/getBooking/{userId}")
    public Response<?> getRegistration(@PathVariable int userId) {
        List<ClassRegistrationDetailsDTO> classRegistrationDetailsDTOS = classRegistrationService.getRegistration((userId));
        if (classRegistrationDetailsDTOS == null) return new Response<>(EHttpStatus.OK, "Không có lớp học đăng ký");
        return new Response<>(EHttpStatus.OK, classRegistrationDetailsDTOS);
    }
    @GetMapping("/getPaymentQr/{regisId}")
    public Response<?> getQr(@PathVariable int regisId){
        return new Response<>(EHttpStatus.OK,classRegistrationService.getPaymentQr(regisId));
    }
    @GetMapping("/getClassList/{userId}")
    public Response<List<ClassListDTO>> getClassList(@PathVariable int userId) {
        return new Response<>(EHttpStatus.OK, classService.getClassListByUserId(userId));
    }

    @GetMapping("/getClassDetail/{className}")
    public Response<?> getClassList(@PathVariable String className) {
        return new Response<>(EHttpStatus.OK, classService.getClassByClassName(className));
    }
    @PostMapping("/createClass")
    public  Response<?> createNewClass(@RequestBody CreateClassRequestDTO dto){
        classRegistrationService.createNewClass(dto);
        return new Response<>(EHttpStatus.OK,"ok");
    }
    @PostMapping("/acceptRegister")
    public  Response<?> acceptResgister(@RequestBody String className){
        return null;
    }
    @GetMapping("/rejectRegister")
    public Response<?> rejectRegister(@PathVariable int registerId){
        return null;
    }
}
