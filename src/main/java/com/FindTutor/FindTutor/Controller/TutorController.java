package com.FindTutor.FindTutor.Controller;

<<<<<<< Updated upstream
import com.FindTutor.FindTutor.Entity.Classes;
import com.FindTutor.FindTutor.Entity.Tutors;
=======

import com.FindTutor.FindTutor.DTO.TutorRequest;
import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.Repository.TutorRepository;
import com.FindTutor.FindTutor.Repository.UserRepository;
>>>>>>> Stashed changes
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.IClassService;
import com.FindTutor.FindTutor.Service.IScheduleService;
import com.FindTutor.FindTutor.Service.ITutorService;
<<<<<<< Updated upstream
=======
import com.FindTutor.FindTutor.DTO.TutorDTO;
>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
<<<<<<< Updated upstream
import java.util.Map;
=======
import java.util.Optional;
>>>>>>> Stashed changes

@RestController
@RequestMapping("/tutors")
public class TutorController {
    @Autowired
    private ITutorService tutorService;

    @Autowired
<<<<<<< Updated upstream
    private IScheduleService scheduleService;

    @Autowired
    private IClassService classService;

    // API lấy danh sách gia sư
    @GetMapping("/getAll")
    public Response<List<Tutors>> getAllTutors() {
        return new Response<>(EHttpStatus.OK, tutorService.getAllTutors());
    }

    // API lấy thông tin chi tiết gia sư + lịch rảnh
    @GetMapping("/detail/{id}")
    public Response<Map<String, Object>> getTutorDetail(@PathVariable Integer id) {
        Tutors tutor = tutorService.getTutorById(id);
        if (tutor == null) {
            return new Response<>(EHttpStatus.NOT_FOUND, null);
        }

        List<?> schedules = scheduleService.getSchedulesByTutorId(id);

        Map<String, Object> result = new HashMap<>();
        result.put("tutor", tutor);
        result.put("schedules", schedules);

        return new Response<>(EHttpStatus.OK, result);
    }

    // API đăng ký học
    @PostMapping("/registerClass")
    public Response<Classes> registerClass(@RequestBody Classes classes) {
        return new Response<>(EHttpStatus.OK, classService.registerClass(classes));
    }
=======
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


>>>>>>> Stashed changes
}
