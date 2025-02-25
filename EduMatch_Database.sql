CREATE DATABASE TutorFinder;
GO
drop database TutorFinder
USE TutorFinder;
GO
CREATE TABLE Users (
                       ID INT IDENTITY(1,1) PRIMARY KEY,
                       username NVARCHAR(50) UNIQUE NOT NULL,
                       password_hash NVARCHAR(255) NOT NULL,
                       fullname NVARCHAR(100) NOT NULL,
                       Email NVARCHAR(100) UNIQUE NOT NULL,
                       phone_number NVARCHAR(15) UNIQUE NOT NULL,
                       Role NVARCHAR(10) CHECK (Role IN ('Student', 'Tutor', 'Admin','System Admin')) NOT NULL,
                       image NVARCHAR(100),
                       created_at DATETIME DEFAULT GETDATE()
);
CREATE TABLE Tutors (
                        ID INT IDENTITY(1,1) PRIMARY KEY,
                        UserID INT UNIQUE NOT NULL,
                        Gender BIT NOT NULL, -- 0: Male, 1: Female
                        DateOfBirth DATE NOT NULL,
                        Address NVARCHAR(255) NOT NULL,
                        Qualification NVARCHAR(255) NOT NULL,
                        Experience INT CHECK (Experience >= 0),
                        Bio NVARCHAR(MAX),
                        Status int,
                        bank_image NVARCHAR(100),
                        money_per_slot money,
                            FOREIGN KEY (UserID) REFERENCES Users(ID) ON DELETE CASCADE
);
CREATE TABLE Students (
                          ID INT IDENTITY(1,1) PRIMARY KEY,
                          UserID INT UNIQUE NOT NULL,
                          ParentName NVARCHAR(100),
                          Grade NVARCHAR(20),
                          Address NVARCHAR(255),
                          Notes NVARCHAR(MAX),
                          FOREIGN KEY (UserID) REFERENCES Users(ID) ON DELETE CASCADE
);
CREATE TABLE Subjects (
                          ID INT IDENTITY(1,1) PRIMARY KEY,
                          subjectname NVARCHAR(100) UNIQUE NOT NULL,
                          Description NVARCHAR(MAX)
);
CREATE TABLE TutorSubjects (
                               ID INT IDENTITY(1,1) PRIMARY KEY,
                               TutorID INT NOT NULL,
                               SubjectID INT NOT NULL,
                               FOREIGN KEY (TutorID) REFERENCES Tutors(ID) ON DELETE CASCADE,
                               FOREIGN KEY (SubjectID) REFERENCES Subjects(ID) ON DELETE NO ACTION,
                               UNIQUE (TutorID, SubjectID)
);
CREATE TABLE Classes (
                         ID INT IDENTITY(1,1) PRIMARY KEY,
                         TutorID INT NOT NULL,
                         SubjectID INT NOT NULL,
                         ClassName NVARCHAR(255) NOT NULL,
                         MaxStudents INT CHECK (MaxStudents > 0),
                         StartDate DATE NOT NULL,
                         EndDate DATE,
                         Status NVARCHAR(20) CHECK (Status IN ('Pending', 'Ongoing', 'Completed')) NOT NULL DEFAULT 'Pending',

                         FOREIGN KEY (TutorID) REFERENCES Tutors(ID) ON DELETE NO ACTION,
                         FOREIGN KEY (SubjectID) REFERENCES Subjects(ID) ON DELETE NO ACTION
);

CREATE TABLE ClassStudents (
                               ID INT IDENTITY(1,1) PRIMARY KEY,
                               ClassID INT NOT NULL,
                               StudentID INT NOT NULL,

                               FOREIGN KEY (ClassID) REFERENCES Classes(ID) ON DELETE NO ACTION,
                               FOREIGN KEY (StudentID) REFERENCES Students(ID),

                               UNIQUE (ClassID, StudentID)
);

CREATE TABLE Schedules (
                           ID INT IDENTITY(1,1) PRIMARY KEY,
                           ClassID INT NOT NULL,
                           Date DATE NOT NULL,
                           StartTime TIME NOT NULL,
                           EndTime TIME NOT NULL,
                           FOREIGN KEY (ClassID) REFERENCES Classes(ID) ON DELETE NO ACTION
);
ALTER TABLE Schedules ADD Status NVARCHAR(20) CHECK (Status IN ('Scheduled', 'Canceled', 'Completed')) DEFAULT 'Scheduled';
CREATE TABLE Reviews (
                         ID INT IDENTITY(1,1) PRIMARY KEY,
                         StudentID INT NOT NULL,
                         TutorID INT NOT NULL,
                         Rating INT CHECK (Rating BETWEEN 1 AND 5) NOT NULL,
                         Comment NVARCHAR(MAX),
                         CreatedAt DATETIME DEFAULT GETDATE(),
                         FOREIGN KEY (StudentID) REFERENCES Students(ID) ,
                         FOREIGN KEY (TutorID) REFERENCES Tutors(ID)
);
CREATE TABLE Messages (
                          ID INT IDENTITY(1,1) PRIMARY KEY,
                          SenderID INT NOT NULL,
                          ReceiverID INT NOT NULL,
                          Message NVARCHAR(MAX) NOT NULL,
                          SentAt DATETIME DEFAULT GETDATE(),
                          FOREIGN KEY (SenderID) REFERENCES Users(ID) ,
                          FOREIGN KEY (ReceiverID) REFERENCES Users(ID)
);
CREATE TABLE Post (
                      ID INT IDENTITY(1,1) PRIMARY KEY,
                      UserID INT NOT NULL,  -- Ng??i ??ng bài
                      SubjectID INT NOT NULL,  -- Môn h?c c?n review
                      Title NVARCHAR(255) NOT NULL,  -- Tiêu ?? bài ??ng
                      Content NVARCHAR(MAX) NOT NULL,  -- N?i dung bài vi?t
                      Rating INT CHECK (Rating BETWEEN 1 AND 5),  -- ?ánh giá t? 1-5 sao
                      created_at DATETIME DEFAULT GETDATE(),  -- Ngày ??ng bài
                      updated_at DATETIME DEFAULT GETDATE(),  -- Ngày c?p nh?t bài

                      FOREIGN KEY (UserID) REFERENCES Users(ID) ,
                      FOREIGN KEY (SubjectID) REFERENCES Subjects(ID)
);
CREATE TABLE TutorAvailability (ID INT IDENTITY(1,1) PRIMARY KEY,
                                TutorID INT NOT NULL,
                                DayOfWeek INT CHECK (DayOfWeek BETWEEN 1 AND 7) NOT NULL, -- 1: Monday, ..., 7: Sunday
                                StartTime TIME NOT NULL,
                                EndTime TIME NOT NULL,
                                Status NVARCHAR(20) CHECK (Status IN ('Available', 'Unavailable', 'On Leave')) DEFAULT 'Available',
                                FOREIGN KEY (TutorID) REFERENCES Tutors(ID) ON DELETE NO ACTION,
                                UNIQUE (TutorID, DayOfWeek, StartTime, EndTime)
);
CREATE TABLE ClassRegistrations (
                                    ID INT IDENTITY(1,1) PRIMARY KEY,
                                    StudentID INT NOT NULL,
                                    ClassID INT,
                                    Status NVARCHAR(20) CHECK (Status IN ('Pending', 'Approved', 'Rejected', 'Canceled')) DEFAULT 'Pending',
                                    RegisteredAt DATETIME DEFAULT GETDATE(),
                                    SubjectName varchar(50) NULL,
                                    Grade NVARCHAR(20) NULL,
                                    PreferredSchedule NVARCHAR(255) NULL,
                                    FOREIGN KEY (StudentID) REFERENCES Students(ID) ON DELETE NO ACTION,
                                    FOREIGN KEY (ClassID) REFERENCES Classes(ID) ON DELETE NO ACTION,
                                    UNIQUE (StudentID, ClassID)
);
INSERT INTO Users (username, password_hash, fullname, Email, phone_number, Role, image)
VALUES
    ('huongtt0410', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Trần Thiên Hương', 'huongtt04102002@gmail.com', '111111111', 'Tutor', NULL),
    ('linhdan_official', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Bùi Thị Linh Đan', 'linhdan.official@gmail.com', '111111112', 'Tutor', NULL),
    ('nguyenmai2607', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Nguyễn Thị Mai', 'nguyenmaihn2607@gmail.com', '111111113', 'Tutor', NULL),
    ('nguyenquechi18103', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Nguyễn Quế Chi', 'nguyenquechi18103@gmail.com', '111111114', 'Tutor', NULL),
    ('minhquantspt1', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Hà Minh Quân', 'minhquantspt1@gmail.com', '111111115', 'Tutor', NULL),
    ('thuylaw1906', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Vương Thị Thanh Thủy', 'thuylaw1906@gmail.com', '111111116', 'Tutor', NULL),
    ('tamntqnguyen03', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Nguyễn Thị Quyết Tâm', 'tamntqnguyen03@gmail.com', '111111117', 'Tutor', NULL),
    ('ngocanh2203', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Trương Ngọc Ánh', 'ngocanh22031999@gmail.com', '111111118', 'Tutor', NULL),
    ('khoidnwork', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Đinh Ngọc Khôi', 'khoidnwork@gmail.com', '111111119', 'Tutor', NULL),
    ('quanghuy2003', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Bùi Quang Huy', 'quanghuy2003@gmail.com', '0345025423', 'Tutor', NULL),
    ('dattiendinh2003', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Đinh Tiến Đạt', 'dattiendinh2003@gmail.com', '111111121', 'Tutor', NULL),
    ('ducsamuelng', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Nguyễn Thái Đức', 'ducsamuelng@gmail.com', '111111131', 'Tutor', NULL),
    ('tien_dung2003', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Đỗ Tiến Dũng', 'tiendung@gmail.com', '111111141', 'Tutor', NULL),
    ('trangttths176143', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Trần Thị Thanh Trang', 'trangttths176143@fpt.edu.vn', '111111151', 'Tutor', NULL),
    ('phamngoch2003', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Phạm Ngọc Hà', 'ngocha@gmail.com', '111111161', 'Tutor', NULL),
    ('trangmun2703', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Dương Quỳnh Trang', 'trangmun2703@gmail.com', '111111171', 'Tutor', NULL),
    ('quanh12102003', '$2a$10$kPMxBz56.g/Gd8/PbmF0S.MqvE7jjmiRygh3rRTtTRGT3fTjEFZWK', N'Đặng Quỳnh Anh', 'quanhquanh12102003@gmail.com', '111111181', 'Tutor', NULL);

INSERT INTO Tutors (UserID, Gender, DateOfBirth, Address, Qualification, Experience, Bio, Status, bank_image)
VALUES
    (1, 1, '2002-10-04', N'Hà Nội', N'Đại học', 4, N'Kinh nghiệm gia sư 4 năm môn toán cấp 2,3', 1, NULL),
    (2, 1, '2003-05-25', N'Hà Nội', N'Đại học', 3, N'Kinh nghiệm gia sư 3 năm môn toán, lý cấp 3, ôn thi THPTQG', 1, NULL),
    (3, 1, '2003-07-26', N'Hà Nội', N'Đại học', 4, N'Kinh nghiệm gia sư 4 năm môn tiếng anh, toán cấp 1,2', 1, NULL),
    (4, 1, '2003-11-18', N'Hà Nội', N'Đại học', 4, N'Kinh nghiệm gia sư 4 năm môn tiếng anh cấp 1,2,3', 1, NULL),
    (5, 0, '2002-07-28', N'Hà Nội', N'Đại học', 3, N'Kinh nghiệm gia sư 3 năm môn toán cấp 2', 1, NULL),
    (6, 1, '2003-06-19', N'Hà Nội', N'Đại học', 3, N'Kinh nghiệm gia sư 3 năm môn toán, anh lớp 8,9', 1, NULL),
    (7, 1, '2003-08-10', N'Hà Nội', N'Đại học', 2, N'Kinh nghiệm gia sư 2 năm môn tiếng anh cấp 2 và 3; 2 năm môn toán cấp 2', 1, NULL),
    (8, 1, '2003-06-22', N'Hà Nội', N'Đại học', 3, N'Kinh nghiệm gia sư 3 năm môn anh cấp 1 và cấp 2', 1, NULL),
    (9, 0, '2003-07-08', N'Hà Nội', N'Đại học', 3, N'Kinh nghiệm gia sư 3 năm môn toán cấp 2', 1, NULL),
    (10, 0, '2003-07-29', N'Hà Nội', N'Đại học', 3, N'Gia sư 3 năm cho học sinh THCS, THPT (6,7,8,9,10)', 1, NULL),
    (11, 0, '2003-09-08', N'Hà Nội', N'Đại học', 1, N'Kinh nghiệm gia sư 1 năm môn tiếng anh cấp 1', 1, NULL),
    (12, 0, '2003-09-25', N'Hà Nội', N'Đại học', 3, N'Kinh nghiệm gia sư 3 năm môn tiếng anh', 1, NULL),
    (13, 0, '2003-03-06', N'Hà Nội', N'Đại học', 2, N'Kinh nghiệm gia sư 2 năm môn toán cấp 3', 1, NULL),
    (14, 1, '2003-04-29', N'Hà Nội', N'Đại học', 1, N'Kinh nghiệm gia sư 01 năm, dạy môn toán cấp 3', 1, NULL),
    (15, 1, '2003-10-23', N'Hà Nội', N'Đại học', 1, N'Kinh nghiệm gia sư 01 năm, dạy toán cấp 1,2,3', 1, NULL),
    (16, 1, '2003-03-27', N'Hà Nội', N'Đại học', 1, N'Kinh nghiệm gia sư 1 năm môn tiếng anh cấp 2,3', 1, NULL),
    (17, 1, '2003-09-08', N'Hà Nội', N'Đại học', 1, N'Kinh nghiệm giảng dạy 01 năm môn tiếng Trung sơ cấp', 1, NULL);
-- Thêm dữ liệu vào bảng Tutors
UPDATE Tutors
SET money_per_slot = CASE ID
                         WHEN 1 THEN 200000 -- Trần Thiên Hương
                         WHEN 2 THEN 250000 -- Bùi Thị Linh Đan
                         WHEN 3 THEN 200000 -- Nguyễn Thị Mai
                         WHEN 4 THEN 200000 -- Nguyễn Quế Chi
                         WHEN 5 THEN 200000 -- Hà Minh Quân
                         WHEN 6 THEN 120000 -- Vương Thị Thanh Thủy (tính theo giờ)
                         WHEN 7 THEN 200000 -- Nguyễn Thị Quyết Tâm
                         WHEN 8 THEN 170000 -- Trương Ngọc Ánh
                         WHEN 9 THEN 200000 -- Đinh Ngọc Khôi
                         WHEN 10 THEN 45000  -- Bùi Quang Huy (45k/buổi/lớp tối đa 15 người)
                         WHEN 11 THEN 160000 -- Đinh Tiến Đạt
                         WHEN 12 THEN 200000 -- Nguyễn Thái Đức
                         WHEN 13 THEN 150000 -- Đỗ Tiến Dũng
                         WHEN 14 THEN 200000 -- Trần Thị Thanh Trang
                         WHEN 15 THEN 150000 -- Phạm Ngọc Hà
                         WHEN 16 THEN 200000 -- Dương Quỳnh Trang
                         WHEN 17 THEN 100000 -- Đặng Quỳnh Anh
    END
WHERE ID IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
