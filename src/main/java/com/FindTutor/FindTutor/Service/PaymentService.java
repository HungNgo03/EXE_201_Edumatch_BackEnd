package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.PaymentQrDTO;
import com.FindTutor.FindTutor.DTO.PaymentQrResponse;
import com.FindTutor.FindTutor.DTO.RegisterIdRequestDTO;
import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.Repository.ClassRegistrationRepository;
import com.FindTutor.FindTutor.Repository.TutorRepository;
import com.FindTutor.FindTutor.Repository.UserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PaymentService implements IPaymentService{
    @Autowired
    private ClassRegistrationRepository registrationRepository;
    @Override
    public void confirmPayment(String json) throws Exception {
        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonArray jsonArray = (JsonArray) jsonObject.get("data");
            System.out.println("data: "+jsonArray);
            // loop array
            for (JsonElement element : jsonArray) {
                JsonObject transaction = element.getAsJsonObject();
                String description = transaction.get("description").getAsString();
                System.out.println(description);
                String amount = transaction.get("amount").getAsString();
                String des[] = description.split(" ");
                if (des.length > 0) {
                    String regex = "(\\d+)(?=-)"; // Tìm số xuất hiện ngay trước dấu '-'
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(description);
                    String result ="";
                    if (matcher.find()) {
                        result = matcher.group(1);
                    }
                    int registerId = Integer.parseInt(result);
                    ClassRegistrations registrations = registrationRepository.getClassRegistrationsByID(registerId);
                    registrations.setPaymentStatus(1);
                    registrationRepository.save(registrations);
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage()
            );
        }
    }

    @Override
    public int checkPayment(RegisterIdRequestDTO requestDTO) {
        String registerIdStr = requestDTO.getRegisterId();
        int registerId = Integer.parseInt(registerIdStr);
        ClassRegistrations registrations = registrationRepository.getClassRegistrationsByID(registerId);
        return registrations.getPaymentStatus();
    }
}
