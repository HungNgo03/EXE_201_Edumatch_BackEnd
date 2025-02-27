package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.PaymentQrDTO;
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

@Service
public class ClassRegistrationService implements IClassRegistrationService{
    @Autowired
    private ClassRegistrationRepository registrationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Override
    public PaymentQrResponse registerStudent(ClassRegistrationRequestDTO request) {
        if (request.getClassId() == null){
            request.setClassId(null);
        }
        Users user = userRepository.findByUsername(request.getUserName());
        Students std = studentRepository.getStudentsByUserID(user.getID());
        ClassRegistrations classRegistrations = new ClassRegistrations(std.getID(), request.getClassId(), "Pending"
                , request.getSubjectId(), request.getGrade(), request.getPreferredSchedule(),0,request.getTutorId());
        ClassRegistrations registrations = registrationRepository.save(classRegistrations);
        //--
        Tutors tutors = tutorRepository.getTutorsByID(request.getTutorId());
        Long registrationId = registrations.getID();
        double totalPrice = tutors.getMoney_per_slot()*10;
        String accountName = "Tran Tuan Minh";
        String bankId = "MB";
        String accountNo = "1020052412003";
        LocalDate month = LocalDate.now();
        String description = request.getUserName()+" hoc phi mon "+request.getSubjectId()+" "+registrationId;
        String qrUrl = "https://img.vietqr.io/image/" + bankId + "-" + accountNo + "-print.png?amount="+totalPrice+"&addInfo=" + description + "&accountName=" + accountName;
        PaymentQrResponse response = new PaymentQrResponse(qrUrl,totalPrice,registrationId);
        return response;
    }
}
