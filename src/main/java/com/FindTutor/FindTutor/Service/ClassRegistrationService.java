package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.ClassRegistrationDetailsDTO;
import com.FindTutor.FindTutor.DTO.CreateClassRequestDTO;
import com.FindTutor.FindTutor.Entity.*;
import com.FindTutor.FindTutor.Repository.*;
import com.FindTutor.FindTutor.DTO.ClassRegistrationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClassRegistrationService implements IClassRegistrationService {
    @Autowired
    private ClassRegistrationRepository registrationRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

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
            Students std = studentRepository.getStudentsByID(classRegistration.getStudentId());
            Users userStd = userRepository.getUsersByID(std.getUserID());
            String paymentstatus = classRegistration.getPaymentStatus() == 0 ? "Chưa hoàn thành" : "Đã hoàn thành";
            ClassRegistrationDetailsDTO dto = new ClassRegistrationDetailsDTO(
                    userStd.getFullname(),
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
    @Override
    public void createNewClass(CreateClassRequestDTO request) {
        ClassRegistrations registration = registrationRepository.getClassRegistrationsByID(request.getRegisterId());
        registration.setStatus("Approved");
        registrationRepository.save(registration);
        int studentId = registration.getStudentId();
        Tutors tutor = tutorRepository.getTutorsByID(registration.getTutorId());
        Subjects subject = subjectRepository.getSubjectsBySubjectname(registration.getSubject());
        Classes newClass = new Classes();
        newClass.setTutor(tutor);
        newClass.setMaxStudent(1);
        newClass.setSubjectId(subject.getID());
        newClass.setClassName("temp");
        newClass.setStartDate(request.getStartDate());
        newClass.setStatus("Ongoing"); // Mặc định chờ duyệt

        newClass = classRepository.save(newClass);
        classRepository.addStudentToClass(newClass.getId(),studentId);
        String className = generateClassName(subject, registration.getGrade(), newClass.getId());
        newClass.setClassName(className);
        classRepository.save(newClass);
        // 5️⃣ Tạo lịch học từ preferredSchedule
        String preferredSchedule = registration.getPreferredSchedule();
        LocalDate startDate = request.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        List<Schedules> schedules = convertToSchedules(preferredSchedule, newClass,startDate);
        scheduleRepository.saveAll(schedules);
    }

    private List<Schedules> convertToSchedules(String preferredSchedule, Classes selectedClass,LocalDate startDate) {
        List<Schedules> schedules = new ArrayList<>();
        List<ScheduleInfo> scheduleInfoList = new ArrayList<>();
        String[] sessions = preferredSchedule.split("\\s*,\\s*");
        for (String session : sessions) {
            String[] parts = session.split("\\s*:\\s*", 2);
            String dayOfWeekVN = parts[0].trim();
            String[] timeRange = parts[1].trim().split("\\s*-\\s*");

            // Chuyển ngày tiếng Việt -> tiếng Anh
            String dayOfWeekEN = convertVietnameseDayToEnglish(dayOfWeekVN);
            LocalTime startTime = LocalTime.parse(timeRange[0]);
            LocalTime endTime = LocalTime.parse(timeRange[1]);
            LocalDate firstDate = getNextDayOfWeekAfter(startDate, dayOfWeekEN);
            // Lưu thông tin từng ngày vào danh sách
            scheduleInfoList.add(new ScheduleInfo(dayOfWeekEN, startTime, endTime, firstDate));
        }
        int index = 0; // Chỉ số để luân phiên giữa các ngày trong danh sách
        while (schedules.size() < 8) { // Tạo 8 buổi
            ScheduleInfo info = scheduleInfoList.get(index);
            Schedules schedule = new Schedules();
            schedule.setClasses(selectedClass);
            schedule.setDate(info.firstDate.plusWeeks(info.weekOffset));
            schedule.setStartTime(info.startTime);
            schedule.setEndTime(info.endTime);
            schedule.setStatus("Scheduled");
            schedules.add(schedule);
            // Cập nhật tuần tiếp theo cho ngày đó
            info.weekOffset++;
            // Chuyển sang ngày tiếp theo trong danh sách
            index = (index + 1) % scheduleInfoList.size();
        }

        return schedules;
    }
    private LocalDate getNextDayOfWeekAfter(LocalDate startDate, String dayOfWeek) {
        Map<String, DayOfWeek> dayMap = Map.of(
                "Monday", DayOfWeek.MONDAY,
                "Tuesday", DayOfWeek.TUESDAY,
                "Wednesday", DayOfWeek.WEDNESDAY,
                "Thursday", DayOfWeek.THURSDAY,
                "Friday", DayOfWeek.FRIDAY,
                "Saturday", DayOfWeek.SATURDAY,
                "Sunday", DayOfWeek.SUNDAY
        );

        DayOfWeek targetDay = dayMap.get(dayOfWeek);
        LocalDate nextDate = startDate.with(TemporalAdjusters.nextOrSame(targetDay));

        // Nếu ngày tìm được trùng với startDate nhưng là ngày trước đó, thì lấy tuần sau
        if (nextDate.isBefore(startDate)) {
            nextDate = nextDate.plusWeeks(1);
        }

        return nextDate;
    }
    // 📌 Lưu thông tin từng ngày học
    private static class ScheduleInfo {
        String dayOfWeek;
        LocalTime startTime;
        LocalTime endTime;
        LocalDate firstDate;
        int weekOffset = 0;

        public ScheduleInfo(String dayOfWeek, LocalTime startTime, LocalTime endTime, LocalDate firstDate) {
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
            this.firstDate = firstDate;
        }
    }


    private LocalDate getNextDayOfWeek(String dayOfWeek) {
        Map<String, DayOfWeek> dayMap = Map.of(
                "Monday", DayOfWeek.MONDAY,
                "Tuesday", DayOfWeek.TUESDAY,
                "Wednesday", DayOfWeek.WEDNESDAY,
                "Thursday", DayOfWeek.THURSDAY,
                "Friday", DayOfWeek.FRIDAY,
                "Saturday", DayOfWeek.SATURDAY,
                "Sunday", DayOfWeek.SUNDAY
        );

        DayOfWeek targetDay = dayMap.get(dayOfWeek);
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.nextOrSame(targetDay));
    }
    private String convertVietnameseDayToEnglish(String dayVN) {
        Map<String, String> dayMap = Map.of(
                "Thứ Hai", "Monday",
                "Thứ Ba", "Tuesday",
                "Thứ Tư", "Wednesday",
                "Thứ Năm", "Thursday",
                "Thứ Sáu", "Friday",
                "Thứ Bảy", "Saturday",
                "Chủ Nhật", "Sunday"
        );
        return dayMap.getOrDefault(dayVN, "Monday"); // Mặc định là Thứ Hai nếu sai
    }


    private String generateClassName(Subjects subject, String grade, int classId) {
        String subjectCode = subject.getSubjectname().replaceAll("\\s+", "").toUpperCase();
        // Format Class ID thành 3 chữ số (001, 002, ..., 100)
        String classCode = String.format("%03d", classId);
        // Ghép lại thành tên lớp
        return subjectCode + "-" + grade + "-" + classCode;
    }

}
