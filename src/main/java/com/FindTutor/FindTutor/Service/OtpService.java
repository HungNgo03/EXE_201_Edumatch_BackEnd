package com.FindTutor.FindTutor.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender; // 🔹 Đảm bảo biến này đã khai báo đúng

    private Map<String, String> otpStorage = new HashMap<>(); // Lưu OTP tạm thời

    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999)); // Tạo OTP 6 chữ số
        otpStorage.put(email, otp); // Lưu OTP vào Map (có thể dùng Redis)
        sendOtpEmail(email, otp);
        return otp;
    }

    public boolean verifyOtp(String email, String inputOtp) {
        String storedOtp = otpStorage.get(email);
        return storedOtp != null && storedOtp.equals(inputOtp);
    }

    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mã OTP đặt lại mật khẩu");
        message.setText("Mã OTP của bạn là: " + otp + "\nMã này có hiệu lực trong 5 phút.");
        mailSender.send(message);
    }
}
