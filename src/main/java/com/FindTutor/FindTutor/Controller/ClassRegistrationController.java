package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.DTO.PaymentQrDTO;
import com.FindTutor.FindTutor.DTO.PaymentQrResponse;
import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.IClassRegistrationService;
import com.FindTutor.FindTutor.DTO.ClassRegistrationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/class")
public class ClassRegistrationController {
    @Autowired
    private IClassRegistrationService classRegistrationService;
    @PostMapping("/register")
    public Response<PaymentQrResponse> registerStudent(@RequestBody ClassRegistrationRequestDTO request) {
        PaymentQrResponse response = classRegistrationService.registerStudent(request);
        return new Response<>(EHttpStatus.OK,response);
    }
}
