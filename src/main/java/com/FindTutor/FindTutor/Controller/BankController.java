package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.DTO.PaymentQrDTO;
import com.FindTutor.FindTutor.DTO.RegisterIdRequestDTO;
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.IPaymentService;
import com.FindTutor.FindTutor.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class BankController {
    @Autowired
    private IPaymentService paymentService;

    @PostMapping("/getPaymentFromBank")
    public void handleWebhook(@RequestBody String payload, @RequestHeader("secure-token") String webhookKey) throws Exception {
        String expectedKey = "FakfLNi92MNKL2n";
        if (!webhookKey.equals(expectedKey)) {
            throw new SecurityException("Invalid webhook key");
        } else {
            paymentService.confirmPayment(payload);
        }
    }
//    @PostMapping("/getPaymentQR")
//    public Response<?> getQrPayment(@RequestBody PaymentQrDTO dto){
//        return new Response<>(EHttpStatus.OK,paymentService.getQrUrl(dto));
//    }
    @PostMapping("/checkPayment")
    public  Response<?> checkPayment(@RequestBody RegisterIdRequestDTO requestDTO){
        return  new Response<>(EHttpStatus.OK,paymentService.checkPayment(requestDTO));
    }
}