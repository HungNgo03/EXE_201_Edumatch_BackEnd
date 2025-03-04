package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.ClassRegistrationDetailsDTO;
import com.FindTutor.FindTutor.DTO.PaymentQrResponse;
import com.FindTutor.FindTutor.Entity.ClassRegistrations;
import com.FindTutor.FindTutor.Entity.Students;
import com.FindTutor.FindTutor.Entity.Tutors;
import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.Repository.ClassRegistrationRepository;
import com.FindTutor.FindTutor.DTO.ClassRegistrationRequestDTO;
import com.FindTutor.FindTutor.Repository.StudentRepository;
import com.FindTutor.FindTutor.Repository.TutorRepository;
import com.FindTutor.FindTutor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassRegistrationService implements IClassRegistrationService {
    @Autowired
    private ClassRegistrationRepository registrationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public ClassRegistrations registerStudent(ClassRegistrationRequestDTO request) {
        if (request.getClassId() == null) {
            request.setClassId(null);
        }
        Users user = userRepository.findByUsername(request.getUserName());
        Students std = studentRepository.getStudentsByUserID(user.getID());
        ClassRegistrations classRegistrations = new ClassRegistrations(std.getID(), request.getClassId(), "Pending"
                , request.getSubjectId(), request.getGrade(), request.getPreferredSchedule(), 0, request.getTutorId());
        return registrationRepository.save(classRegistrations);
        //--
//        Tutors tutors = tutorRepository.getTutorsByID(request.getTutorId());
//        Long registrationId = registrations.getID();
//        double totalPrice = tutors.getMoney_per_slot() * 10;
//        String accountName = "Tran Tuan Minh";
//        String bankId = "MB";
//        String accountNo = "1020052412003";
//        LocalDate month = LocalDate.now();
//        String description = request.getUserName() + " hoc phi mon " + request.getSubjectId() + " " + registrationId;
//        String qrUrl = "https://img.vietqr.io/image/" + bankId + "-" + accountNo + "-print.png?amount=" + totalPrice + "&addInfo=" + description + "&accountName=" + accountName;
//        PaymentQrResponse response = new PaymentQrResponse(qrUrl, totalPrice, registrationId);
    }

    @Override
    public List<ClassRegistrationDetailsDTO> getRegistration(int userId) {
        Users user = userRepository.getUsersByID(userId);
        List<ClassRegistrations> classRegistrationsList = null;
        if (user.getRole().equals("Student")) {
            Students std = studentRepository.getStudentsByUserID(userId);
            classRegistrationsList = registrationRepository.getClassRegistrationsByStudentId(std.getID());
        }
        if (user.getRole().equals("Tutor")) {
            Tutors tutor = tutorRepository.getTutorsByUserID(userId);
            classRegistrationsList = registrationRepository.getClassRegistrationsByTutorId(tutor.getID());
        }
        if (classRegistrationsList == null || classRegistrationsList.isEmpty()) {
           return null;
        }

        List<ClassRegistrationDetailsDTO> registrationDetailsList = new ArrayList<>();

        for (ClassRegistrations classRegistration : classRegistrationsList) {
            Tutors tutor = tutorRepository.getTutorsByID(classRegistration.getTutorId());
            Users userTutor = userRepository.getUsersByID(tutor.getUserID());
            String tutorName = userTutor.getFullname();
            String paymentstatus = classRegistration.getPaymentStatus() == 0 ? "Chưa hoàn thành" : "Đã hoàn thành";
            ClassRegistrationDetailsDTO dto = new ClassRegistrationDetailsDTO(
                    classRegistration.getID(),
                    classRegistration.getSubject(),
                    classRegistration.getStatus(),
                    classRegistration.getPreferredSchedule(),
                    tutorName,
                    classRegistration.getRegisteredAt(),
                    classRegistration.getGrade(),
                    paymentstatus
            );
            registrationDetailsList.add(dto);
        }
        return registrationDetailsList;
    }

    @Override
    public String getPaymentQr(int registrationId) {
        ClassRegistrations classRegistrations = registrationRepository.getClassRegistrationsByID(registrationId);
        Tutors tutors = tutorRepository.getTutorsByID(classRegistrations.getTutorId());
        Students std = studentRepository.getStudentsByID(classRegistrations.getStudentId());
        Users user = userRepository.getUsersByID(std.getUserID());
        double totalPrice = tutors.getMoney_per_slot() * 10;
        String accountName = "Tran Tuan Minh";
        String bankId = "MB";
        String accountNo = "1020052412003";
        LocalDate month = LocalDate.now();
        String description = user.getUsername() + " hoc phi mon " + classRegistrations.getSubject() + " " + registrationId;
        String qrUrl = "https://img.vietqr.io/image/" + bankId + "-" + accountNo + "-print.png?amount=" + totalPrice + "&addInfo=" + description + "&accountName=" + accountName;
        return qrUrl;
    }
}
