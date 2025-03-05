package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.DTO.ChangePasswordRequest;
import com.FindTutor.FindTutor.DTO.UpdateRequest;
import com.FindTutor.FindTutor.Entity.Students;
import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.DTO.LoginRequest;
import com.FindTutor.FindTutor.Repository.StudentRepository;
import com.FindTutor.FindTutor.Repository.TutorRepository;
import com.FindTutor.FindTutor.Repository.UserRepository;
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.OtpService;
import com.FindTutor.FindTutor.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import com.FindTutor.FindTutor.DTO.RegisterRequest;


import jakarta.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://edumatch.click",allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpService otpService;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        Users user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Email không tồn tại trong hệ thống");
        }

        String otp = otpService.generateOtp(email);
        return ResponseEntity.ok("OTP đã được gửi đến email của bạn");
    }

    // Bước 2: Xác nhận OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        if (otpService.verifyOtp(email, otp)) {
            return ResponseEntity.ok("OTP hợp lệ");
        }
        return ResponseEntity.badRequest().body("OTP không hợp lệ");
    }

    // Bước 3: Đặt lại mật khẩu
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        if (!otpService.verifyOtp(email, otp)) {
            return ResponseEntity.badRequest().body("OTP không hợp lệ");
        }

        Users user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Email không tồn tại");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userService.updateUser(user); // Cập nhật mật khẩu mới

        return ResponseEntity.ok("Mật khẩu đã được đặt lại thành công");
    }


    // Đăng ký tài khoản
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        System.out.println("Received user: " + request.getUsername() + ", Password: " + request.getPassword());

        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username '" + request.getUsername() + "' already taken");
        }
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        if (userService.existsByPhoneNumber(request.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Phone number already in use");
        }
        if (request.getPassword() == null) {
            return ResponseEntity.badRequest().body("Password cannot be null");
        }

        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Lưu thông tin User
        Users newUser = new Users();
        newUser.setUsername(request.getUsername());
        newUser.setPasswordHash(encodedPassword);
        newUser.setFullname(request.getFullname());
        newUser.setEmail(request.getEmail());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setRole(request.getRole());
        newUser.setCreatedAt(new Date());

        Users savedUser = userService.registerUser(newUser); // Lưu vào DB

        // Nếu role là Tutor, lưu vào bảng Tutors
        if ("Tutor".equals(request.getRole())) {
            Tutors tutor = new Tutors();
            tutor.setUserID(savedUser.getID()); // Gán UserID từ Users
            tutor.setGender(request.getGender());
            tutor.setDateOfBirth(request.getDateOfBirth());
            tutor.setAddress(request.getAddress());
            tutor.setQualification(request.getQualification());
            tutor.setExperience(request.getExperience());
            tutor.setBio(request.getBio());
            tutor.setStatus(1); // Active mặc định

            tutorRepository.save(tutor);
        }

        // Nếu role là Student, lưu vào bảng Students
        if ("Student".equals(request.getRole())) {
            Students student = new Students();
            student.setUserID(savedUser.getID()); // Gán UserID từ Users
            student.setParentName(request.getParentName());
            student.setGrade(request.getGrade());
            student.setAddress(request.getAddress());
            student.setNotes(request.getNotes());

            studentRepository.save(student);
        }

        return ResponseEntity.ok("User registered successfully");
    }



    // Đăng nhập tài khoản

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Users user = userService.findByUsername(loginRequest.getUsername());

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        // Lưu user vào session
        session.setAttribute("user", user);

        return ResponseEntity.ok(Map.of(
                "message", "Login Successful",
                "userID", user.getID(),
                "username", user.getUsername(),
                "fullname", user.getFullname(),
                "phoneNumber",user.getPhoneNumber(),
                "email", user.getEmail(),
                "role", user.getRole()
        ));
    }


    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable int userId) {
        Optional<Users> userOptional = userService.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Users user = userOptional.get();

        // Nếu là Tutor, lấy thông tin từ bảng Tutors
        if ("Tutor".equals(user.getRole())) {
            Optional<Tutors> tutorOptional = tutorRepository.findByUserID(user.getID());
            if (tutorOptional.isPresent()) {
                return ResponseEntity.ok(tutorOptional.get());
            }
        }

        // Nếu là Student, lấy thông tin từ bảng Students
        if ("Student".equals(user.getRole())) {
            Optional<Students> studentOptional = studentRepository.findByUserID(user.getID());
            if (studentOptional.isPresent()) {
                return ResponseEntity.ok(studentOptional.get());
            }
        }

        return ResponseEntity.ok(user); // Nếu là Admin/System Admin, chỉ trả về thông tin User
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }

    private static final String UPLOAD_DIR = "uploads/"; // Thư mục lưu ảnh

    @PutMapping("/update-image/{id}")
    public ResponseEntity<String> updateUserImage(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Users user = userOptional.get();

        try {
            // Xóa ảnh cũ nếu có
            if (user.getImage() != null) {
                File oldFile = new File(UPLOAD_DIR + user.getImage());
                if (oldFile.exists() && oldFile.isFile()) {
                    oldFile.delete(); // Xóa file ảnh cũ
                }
            }

            // Lưu ảnh mới vào thư mục uploads
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, file.getBytes());

            // Cập nhật ảnh mới vào database
            user.setImage(fileName);
            userRepository.save(user);

            return ResponseEntity.ok("Image updated successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving image");
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getUserImage(@PathVariable int id) {
        try {
            // Tìm user trong database
            Users user = userRepository.findById(id).orElse(null);
            if (user == null || user.getImage() == null) {
                return ResponseEntity.notFound().build();
            }

            // Đường dẫn đến ảnh
            Path filePath = Paths.get(UPLOAD_DIR + user.getImage());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                // Xác định loại file ảnh
                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        Optional<Users> userOptional = userRepository.findById(request.getId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        Users user = userOptional.get();
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
            return ResponseEntity.badRequest().body("Mật khẩu cũ không chính xác");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Mật khẩu đã được cập nhật thành công");
    }


    @PutMapping("/update-profile/{id}")
    public Response<?> updateUserInfo(@PathVariable int id, @RequestBody UpdateRequest request) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return new Response<>(EHttpStatus.NOT_FOUND,"User không tồn tại");
        }
        if (userService.existsByUsername2(request.getUsername(), id)) {
            return new Response<>(EHttpStatus.ALREADY_EXISTS,"Username '"  + "' already taken");
        }
        if (userService.existsByEmail2(request.getEmail(),id)) {
            return new Response<>(EHttpStatus.ALREADY_EXISTS,"Email already in use");
        }
        if (userService.existsByPhoneNumber2(request.getPhoneNumber(),id)) {
            return new Response<>(EHttpStatus.ALREADY_EXISTS,"Phone number already in use");
        }

        Users user = userOptional.get();

        // Cập nhật thông tin người dùng
        user.setUsername(request.getUsername());
        user.setFullname(request.getFullname());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());

        userRepository.save(user);

        // Nếu là Tutor -> cập nhật trong bảng Tutors
        if ("Tutor".equalsIgnoreCase(request.getRole())) {
            Optional<Tutors> tutorOptional = tutorRepository.findByUserID(id);
            if (tutorOptional.isPresent()) {
                Tutors tutor = tutorOptional.get();
                tutor.setGender(request.getGender());
                tutor.setDateOfBirth(request.getDateOfBirth());
                tutor.setAddress(request.getAddress());
                tutor.setQualification(request.getQualification());
                tutor.setExperience(request.getExperience());
                tutor.setBio(request.getBio());
                tutorRepository.save(tutor);
            }
        }

        // Nếu là Student -> cập nhật trong bảng Students
        if ("Student".equalsIgnoreCase(request.getRole())) {
            Optional<Students> studentOptional = studentRepository.findByUserID(id);
            if (studentOptional.isPresent()) {
                Students student = studentOptional.get();
                student.setParentName(request.getParentName());
                student.setGrade(request.getGrade());
                student.setAddress(request.getAddress());
                student.setNotes(request.getNotes());
                studentRepository.save(student);
            }
        }
        return new Response<>(EHttpStatus.OK,"Cập nhật thành công");
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {

            // Tìm user trong database
            Optional<Users> userOptional = userService.findById(id);
            if (userOptional == null ) {
                return ResponseEntity.notFound().build();
            }
            Users user = userOptional.get();

            return ResponseEntity.ok(user);



    }
}
