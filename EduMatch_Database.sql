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
INSERT INTO Subjects (subjectname, Description) VALUES
                                                    (N'Toán học', N'Môn học về số học, đại số, hình học và giải tích.'),
                                                    (N'Ngữ văn', N'Học về văn học Việt Nam và thế giới, ngữ pháp, và kỹ năng viết.'),
                                                    (N'Tiếng Anh', N'Môn học về ngôn ngữ tiếng Anh, bao gồm ngữ pháp, từ vựng và giao tiếp.'),
                                                    (N'Lịch sử', N'Nghiên cứu về lịch sử Việt Nam và thế giới.'),
                                                    (N'Địa lý', N'Tìm hiểu về địa lý tự nhiên, kinh tế và chính trị của Việt Nam và thế giới.'),
                                                    (N'Vật lý', N'Học về cơ học, nhiệt học, điện từ học, quang học và vật lý hạt nhân.'),
                                                    (N'Hóa học', N'Nghiên cứu về các chất, phản ứng hóa học và ứng dụng trong cuộc sống.'),
                                                    (N'Sinh học', N'Tìm hiểu về cơ thể sống, sinh thái và môi trường.'),
                                                    (N'Giáo dục công dân', N'Học về đạo đức, pháp luật, công dân và xã hội.'),
                                                    (N'Tin học', N'Nghiên cứu về máy tính, lập trình và công nghệ thông tin.'),
                                                    (N'Công nghệ', N'Môn học về kỹ thuật, sản xuất và ứng dụng công nghệ trong đời sống.'),
                                                    (N'Giáo dục thể chất', N'Rèn luyện thể lực, kỹ năng vận động và lối sống lành mạnh.'),
                                                    (N'Âm nhạc', N'Học về nhạc lý, cảm thụ âm nhạc và thực hành biểu diễn.'),
                                                    (N'Mỹ thuật', N'Nghiên cứu về hội họa, điêu khắc và nghệ thuật tạo hình.'),
                                                    (N'Giáo dục quốc phòng', N'Học về an ninh, quốc phòng và kỹ năng quân sự cơ bản.');
INSERT INTO TutorSubjects (TutorID, SubjectID)
VALUES
    (1, 1), -- Trần Thiên Hương - Toán
    (2, 1), -- Bùi Thị Linh Đan - Toán
    (2, 2), -- Bùi Thị Linh Đan - Lý
    (3, 1), -- Nguyễn Thị Mai - Toán
    (3, 4), -- Nguyễn Thị Mai - Anh
    (4, 4), -- Nguyễn Quế Chi - Anh
    (5, 1), -- Hà Minh Quân - Toán
    (6, 1), -- Vương Thị Thanh Thủy - Toán
    (6, 4), -- Vương Thị Thanh Thủy - Anh
    (7, 1), -- Nguyễn Thị Quyết Tâm - Toán
    (7, 4), -- Nguyễn Thị Quyết Tâm - Anh
    (8, 4), -- Trương Ngọc Ánh - Anh
    (9, 3), -- Đinh Ngọc Khôi - Hóa
    (10, 4), -- Bùi Quang Huy - Anh
    (11, 4), -- Đinh Tiến Đạt - Anh
    (12, 4), -- Nguyễn Thái Đức - Anh
    (13, 1), -- Đỗ Tiến Dũng - Toán
    (14, 1), -- Trần Thị Thanh Trang - Toán
    (15, 1), -- Phạm Ngọc Hà - Toán
    (16, 4), -- Dương Quỳnh Trang - Anh
    (17, 5); -- Đặng Quỳnh Anh - Trung
INSERT INTO Users (username, password_hash, fullname, Email, phone_number, Role, image, created_at)
VALUES
    ('nguyenvanan', 'hashed_password_1', N'Nguyễn Văn An', 'nguyenvanan23@gmail.com', '0912345678', 'Student', NULL, GETDATE()),
    ('tranbichngoc', 'hashed_password_2', N'Trần Bích Ngọc', 'tranbichngoc45@gmail.com', '0812345679', 'Student', NULL, GETDATE()),
    ('leminhthuan', 'hashed_password_3', N'Lê Minh Thuận', 'leminhthuan67@gmail.com', '0312345680', 'Student', NULL, GETDATE()),
    ('phamthithanh', 'hashed_password_4', N'Phạm Thị Thanh', 'phamthithanh89@gmail.com', '0912345681', 'Student', NULL, GETDATE()),
    ('hoangquangvinh', 'hashed_password_5', N'Hoàng Quang Vinh', 'hoangquangvinh12@gmail.com', '0812345682', 'Student', NULL, GETDATE()),
    ('dangthihong', 'hashed_password_6', N'Đặng Thị Hồng', 'dangthihong34@gmail.com', '0312345683', 'Student', NULL, GETDATE()),
    ('ngothanhlam', 'hashed_password_7', N'Ngô Thanh Lâm', 'ngothanhlam56@gmail.com', '0912345684', 'Student', NULL, GETDATE()),
    ('vuthithuy', 'hashed_password_8', N'Vũ Thị Thủy', 'vuthithuy78@gmail.com', '0812345685', 'Student', NULL, GETDATE()),
    ('buitrungkien', 'hashed_password_9', N'Bùi Trung Kiên', 'buitrungkien90@gmail.com', '0312345686', 'Student', NULL, GETDATE()),
    ('doanminhchau', 'hashed_password_10', N'Đoàn Minh Châu', 'doanminhchau21@gmail.com', '0912345687', 'Student', NULL, GETDATE()),
    ('nguyenhuynhnhu', 'hashed_password_11', N'Nguyễn Huỳnh Như', 'nguyenhuynhnhu43@gmail.com', '0812345688', 'Student', NULL, GETDATE()),
    ('tranquanghuy', 'hashed_password_12', N'Trần Quang Huy', 'tranquanghuy65@gmail.com', '0312345689', 'Student', NULL, GETDATE()),
    ('lethithao', 'hashed_password_13', N'Lê Thị Thảo', 'lethithao87@gmail.com', '0912345690', 'Student', NULL, GETDATE()),
    ('phamminhduc', 'hashed_password_14', N'Phạm Minh Đức', 'phamminhduc09@gmail.com', '0812345691', 'Student', NULL, GETDATE()),
    ('hoangthilan', 'hashed_password_15', N'Hoàng Thị Lan', 'hoangthilan32@gmail.com', '0312345692', 'Student', NULL, GETDATE()),
    ('dangquocbao', 'hashed_password_16', N'Đặng Quốc Bảo', 'dangquocbao54@gmail.com', '0912345693', 'Student', NULL, GETDATE()),
    ('ngothanhphong', 'hashed_password_17', N'Ngô Thanh Phong', 'ngothanhphong76@gmail.com', '0812345694', 'Student', NULL, GETDATE()),
    ('vudinhkhoa', 'hashed_password_18', N'Vũ Đình Khoa', 'vudinhkhoa98@gmail.com', '0312345695', 'Student', NULL, GETDATE()),
    ('buihongnhung', 'hashed_password_19', N'Bùi Hồng Nhung', 'buihongnhung10@gmail.com', '0912345696', 'Student', NULL, GETDATE()),
    ('doanthithuy', 'hashed_password_20', N'Đoàn Thị Thúy', 'doanthithuy22@gmail.com', '0812345697', 'Student', NULL, GETDATE()),
    ('nguyenminhtri', 'hashed_password_21', N'Nguyễn Minh Trí', 'nguyenminhtri44@gmail.com', '0312345698', 'Student', NULL, GETDATE()),
    ('tranngocmai', 'hashed_password_22', N'Trần Ngọc Mai', 'tranngocmai66@gmail.com', '0912345699', 'Student', NULL, GETDATE()),
    ('lequangminh', 'hashed_password_23', N'Lê Quang Minh', 'lequangminh88@gmail.com', '0812345700', 'Student', NULL, GETDATE()),
    ('phamthihuyen', 'hashed_password_24', N'Phạm Thị Huyền', 'phamthihuyen11@gmail.com', '0312345701', 'Student', NULL, GETDATE()),
    ('hoangdinhphuc', 'hashed_password_25', N'Hoàng Đình Phúc', 'hoangdinhphuc33@gmail.com', '0912345702', 'Student', NULL, GETDATE()),
    ('dangthiminh', 'hashed_password_26', N'Đặng Thị Minh', 'dangthiminh55@gmail.com', '0812345703', 'Student', NULL, GETDATE()),
    ('ngothanhdat', 'hashed_password_27', N'Ngô Thanh Đạt', 'ngothanhdat77@gmail.com', '0312345704', 'Student', NULL, GETDATE()),
    ('vuthithanh', 'hashed_password_28', N'Vũ Thị Thanh', 'vuthithanh99@gmail.com', '0912345705', 'Student', NULL, GETDATE()),
    ('buitrungnghia', 'hashed_password_29', N'Bùi Trung Nghĩa', 'buitrungnghia20@gmail.com', '0812345706', 'Student', NULL, GETDATE()),
    ('doanminhquan', 'hashed_password_30', N'Đoàn Minh Quân', 'doanminhquan42@gmail.com', '0312345707', 'Student', NULL, GETDATE()),
    ('nguyenhuynhphuc', 'hashed_password_31', N'Nguyễn Huỳnh Phúc', 'nguyenhuynhphuc64@gmail.com', '0912345708', 'Student', NULL, GETDATE()),
    ('tranngoclam', 'hashed_password_32', N'Trần Ngọc Lâm', 'tranngoclam86@gmail.com', '0812345709', 'Student', NULL, GETDATE()),
    ('lethithu', 'hashed_password_33', N'Lê Thị Thư', 'lethithu08@gmail.com', '0312345710', 'Student', NULL, GETDATE()),
    ('phamminhkhanh', 'hashed_password_34', N'Phạm Minh Khánh', 'phamminhkhanh31@gmail.com', '0912345711', 'Student', NULL, GETDATE()),
    ('hoangthimien', 'hashed_password_35', N'Hoàng Thị Miên', 'hoangthimien53@gmail.com', '0812345712', 'Student', NULL, GETDATE()),
    ('dangquocan', 'hashed_password_36', N'Đặng Quốc Ân', 'dangquocan75@gmail.com', '0312345713', 'Student', NULL, GETDATE()),
    ('ngothanhhien', 'hashed_password_37', N'Ngô Thanh Hiền', 'ngothanhhien97@gmail.com', '0912345714', 'Student', NULL, GETDATE()),
    ('vudinhphu', 'hashed_password_38', N'Vũ Đình Phú', 'vudinhphu19@gmail.com', '0812345715', 'Student', NULL, GETDATE()),
    ('buihongthuy', 'hashed_password_39', N'Bùi Hồng Thủy', 'buihongthuy41@gmail.com', '0312345716', 'Student', NULL, GETDATE()),
    ('doanthithao', 'hashed_password_40', N'Đoàn Thị Thảo', 'doanthithao63@gmail.com', '0912345717', 'Student', NULL, GETDATE()),
    ('nguyenminhvu', 'hashed_password_41', N'Nguyễn Minh Vũ', 'nguyenminhvu85@gmail.com', '0812345718', 'Student', NULL, GETDATE()),
    ('tranngocyen', 'hashed_password_42', N'Trần Ngọc Yến', 'tranngocyen07@gmail.com', '0312345719', 'Student', NULL, GETDATE()),
    ('lequangthai', 'hashed_password_43', N'Lê Quang Thái', 'lequangthai29@gmail.com', '0912345720', 'Student', NULL, GETDATE()),
    ('phamthithu', 'hashed_password_44', N'Phạm Thị Thư', 'phamthithu51@gmail.com', '0812345721', 'Student', NULL, GETDATE()),
    ('hoangdinhkhang', 'hashed_password_45', N'Hoàng Đình Khang', 'hoangdinhkhang73@gmail.com', '0312345722', 'Student', NULL, GETDATE()),
    ('dangthiminhthu', 'hashed_password_46', N'Đặng Thị Minh Thư', 'dangthiminhthu95@gmail.com', '0912345723', 'Student', NULL, GETDATE()),
    ('ngothanhkhoa', 'hashed_password_47', N'Ngô Thanh Khoa', 'ngothanhkhoa17@gmail.com', '0812345724', 'Student', NULL, GETDATE()),
    ('vuthithanhnga', 'hashed_password_48', N'Vũ Thị Thanh Nga', 'vuthithanhnga39@gmail.com', '0312345725', 'Student', NULL, GETDATE()),
    ('buitrungthanh', 'hashed_password_49', N'Bùi Trung Thành', 'buitrungthanh61@gmail.com', '0912345726', 'Student', NULL, GETDATE()),
    ('doanminhthang', 'hashed_password_50', N'Đoàn Minh Thắng', 'doanminhthang83@gmail.com', '0812345727', 'Student', NULL, GETDATE())

    INSERT INTO Students (UserID, ParentName, Grade, Address, Notes)
VALUES
    (18, N'Nguyễn Văn Hùng', '10', N'Hà Nội', N'Chăm chỉ, cần cải thiện môn Toán'),
    (19, N'Trần Thị Mai', '11', N'Hà Nội', N'Tích cực tham gia hoạt động ngoại khóa'),
    (20, N'Lê Văn Tâm', '9', N'Hà Nội', N'Cần chú ý hơn trong giờ học'),
    (21, N'Phạm Văn Nam', '12', N'Hà Nội', N'Học tốt môn Văn'),
    (22, N'Hoàng Thị Hoa', '10', N'Hà Nội', N'Có năng khiếu hội họa'),
    (23, N'Đặng Văn Long', '11', N'Hà Nội', N'Cần cải thiện kỹ năng giao tiếp'),
    (24, N'Ngô Thị Lan', '9', N'Hà Nội', N'Học đều các môn'),
    (25, N'Vũ Văn Dũng', '12', N'Hà Nội', N'Có khả năng lãnh đạo tốt'),
    (26, N'Bùi Thị Hạnh', '10', N'Hà Nội', N'Tích cực tham gia câu lạc bộ tiếng Anh'),
    (27, N'Đoàn Văn Tài', '11', N'Hà Nội', N'Cần cải thiện môn Lý'),
    (28, N'Nguyễn Thị Ngọc', '9', N'Hà Nội', N'Học tốt môn Hóa'),
    (29, N'Trần Văn Khánh', '12', N'Hà Nội', N'Có năng khiếu thể thao'),
    (30, N'Lê Thị Hồng', '10', N'Hà Nội', N'Cần chú ý hơn trong giờ học'),
    (31, N'Phạm Văn Hùng', '11', N'Hà Nội', N'Học tốt môn Sử'),
    (32, N'Hoàng Thị Thắm', '9', N'Hà Nội', N'Tích cực tham gia hoạt động xã hội'),
    (33, N'Đặng Văn Bình', '12', N'Hà Nội', N'Cần cải thiện môn Địa'),
    (34, N'Ngô Thị Thu', '10', N'Hà Nội', N'Học đều các môn'),
    (35, N'Vũ Văn Nam', '11', N'Hà Nội', N'Có năng khiếu âm nhạc'),
    (36, N'Bùi Thị Mai', '9', N'Hà Nội', N'Cần cải thiện kỹ năng viết'),
    (37, N'Đoàn Văn Hùng', '12', N'Hà Nội', N'Học tốt môn Sinh'),
    (38, N'Nguyễn Thị Lan', '10', N'Hà Nội', N'Tích cực tham gia hoạt động ngoại khóa'),
    (39, N'Trần Thị Hương', '11', N'Hà Nội', N'Cần chú ý hơn trong giờ học'),
    (40, N'Lê Văn Tài', '9', N'Hà Nội', N'Học tốt môn Toán'),
    (41, N'Phạm Thị Ngọc', '12', N'Hà Nội', N'Có năng khiếu hội họa'),
    (42, N'Hoàng Văn Long', '10', N'Hà Nội', N'Cần cải thiện môn Văn'),
    (43, N'Đặng Thị Hoa', '11', N'Hà Nội', N'Học đều các môn'),
    (44, N'Ngô Văn Dũng', '9', N'Hà Nội', N'Tích cực tham gia câu lạc bộ tiếng Anh'),
    (45, N'Vũ Thị Hạnh', '12', N'Hà Nội', N'Cần cải thiện môn Lý'),
    (46, N'Bùi Văn Tài', '10', N'Hà Nội', N'Học tốt môn Hóa'),
    (47, N'Đoàn Thị Hồng', '11', N'Hà Nội', N'Có năng khiếu thể thao'),
    (48, N'Nguyễn Văn Nam', '9', N'Hà Nội', N'Cần chú ý hơn trong giờ học'),
    (49, N'Trần Thị Thắm', '12', N'Hà Nội', N'Học tốt môn Sử'),
    (50, N'Lê Thị Thu', '10', N'Hà Nội', N'Tích cực tham gia hoạt động xã hội'),
    (51, N'Phạm Văn Bình', '11', N'Hà Nội', N'Cần cải thiện môn Địa'),
    (52, N'Hoàng Thị Lan', '9', N'Hà Nội', N'Học đều các môn'),
    (53, N'Đặng Văn Hùng', '12', N'Hà Nội', N'Có năng khiếu âm nhạc'),
    (54, N'Ngô Thị Ngọc', '10', N'Hà Nội', N'Cần cải thiện kỹ năng viết'),
    (55, N'Vũ Văn Long', '11', N'Hà Nội', N'Học tốt môn Sinh'),
    (56, N'Bùi Thị Hoa', '9', N'Hà Nội', N'Tích cực tham gia hoạt động ngoại khóa'),
    (57, N'Đoàn Thị Hạnh', '12', N'Hà Nội', N'Cần chú ý hơn trong giờ học'),
    (58, N'Nguyễn Văn Dũng', '10', N'Hà Nội', N'Học tốt môn Toán'),
    (59, N'Trần Thị Mai', '11', N'Hà Nội', N'Có năng khiếu hội họa'),
    (60, N'Lê Văn Hùng', '9', N'Hà Nội', N'Cần cải thiện môn Văn'),
    (61, N'Phạm Thị Lan', '12', N'Hà Nội', N'Học đều các môn'),
    (62, N'Hoàng Văn Tài', '10', N'Hà Nội', N'Tích cực tham gia câu lạc bộ tiếng Anh'),
    (63, N'Đặng Thị Thu', '11', N'Hà Nội', N'Cần cải thiện môn Lý'),
    (64, N'Ngô Văn Nam', '9', N'Hà Nội', N'Học tốt môn Hóa'),
    (65, N'Vũ Thị Ngọc', '12', N'Hà Nội', N'Có năng khiếu thể thao'),
    (66, N'Bùi Văn Long', '10', N'Hà Nội', N'Cần chú ý hơn trong giờ học'),
    (67, N'Đoàn Văn Hùng', '11', N'Hà Nội', N'Học tốt môn Sử');
INSERT INTO Post (UserID, SubjectID, Title, Content, Rating, created_at, updated_at) VALUES
                                                                                         (18, 1, N'Phương trình bậc hai', N'Hôm nay học giải phương trình bậc hai trong môn Toán. Ban đầu mình hơi bối rối vì công thức tính delta phức tạp, nhưng khi làm bài tập thì thấy thú vị. Thầy giảng chậm rãi, giải thích từng bước nên dần dần mình hiểu. Mình thích cách môn học này rèn tư duy logic, dù đôi khi gặp bài khó là muốn bỏ cuộc. Rating 4 vì vẫn cần luyện thêm để tự tin hơn.', 4, '2025-02-28 08:30:00', '2025-02-28 08:30:00'),
                                                                                         (19, 2, N'Tràng Giang của Huy Cận', N'Học bài Tràng Giang trong Ngữ văn, mình thật sự bị cuốn hút bởi cảm xúc buồn man mác của tác giả. Thầy phân tích từng câu thơ, từ hình ảnh bèo dạt đến cảnh sông dài, làm mình cảm nhận sâu sắc nỗi cô đơn. Mình thích cách thơ gợi lên suy nghĩ về cuộc đời, dù nhớ hết ý chính hơi khó. Môn này giúp mình yêu văn học hơn. Đánh giá 5 sao!', 5, '2025-02-28 10:15:00', '2025-02-28 10:15:00'),
                                                                                         (20, 3, N'Từ vựng tiếng Anh mới', N'Hôm nay học từ vựng tiếng Anh, chủ đề về môi trường. Mình thấy thú vị vì từ mới khá thực tế, nhưng phát âm thì vẫn là thử thách lớn. Thầy cho nghe audio, mình cố bắt chước mà vẫn chưa chuẩn. Ngữ pháp thì ổn, nhưng vốn từ còn hạn chế nên viết câu dài hơi khó. Mình cho 3 sao vì cần cố gắng nhiều hơn để tự tin giao tiếp.', 3, '2025-02-28 13:45:00', '2025-02-28 13:45:00'),
                                                                                         (21, 4, N'Chiến thắng Điện Biên Phủ', N'Học về chiến thắng Điện Biên Phủ trong môn Lịch sử, mình thật sự tự hào. Thầy kể chi tiết về sự hy sinh của bộ đội và chiến lược tài tình của Đại tướng Võ Nguyên Giáp. Mình tưởng tượng cảnh quân ta vượt núi, đánh địch, cảm giác rất xúc động. Dù ngày tháng hơi khó nhớ, nhưng câu chuyện này đáng để ghi lòng. Đánh giá 5 sao vì quá tuyệt vời!', 5, '2025-02-28 16:00:00', '2025-02-28 16:00:00'),
                                                                                         (22, 7, N'Phản ứng hóa học cơ bản', N'Hôm nay môn Hóa học làm thí nghiệm tạo khí. Mình thích phần thực hành vì được tự tay làm, thấy phản ứng sủi bọt rất thú vị. Nhưng lý thuyết về cân bằng hóa học thì hơi rối, mình chưa hiểu hết cách tính. Thầy giải thích nhiệt tình, nhưng mình cần xem lại bài. Đánh giá 3 sao vì thích thực hành hơn lý thuyết.', 3, '2025-02-28 18:20:00', '2025-02-28 18:20:00'),
                                                                                         (23, 1, N'Hình học không gian', N'Học hình học không gian trong Toán, mình thấy khó tưởng tượng các khối. Thầy vẽ hình minh họa, hướng dẫn cách tính thể tích, nhưng mình vẫn nhầm lẫn giữa các công thức. Làm bài tập thì dần quen, cảm giác giải được bài rất đã. Môn này thử thách tư duy, dù mình chưa giỏi lắm. Đánh giá 3 sao, cần luyện thêm.', 3, '2025-03-01 09:00:00', '2025-03-01 09:00:00'),
                                                                                         (24, 2, N'Chí Phèo của Nam Cao', N'Đọc Chí Phèo trong Ngữ văn, mình thấy thương nhân vật quá. Thầy giảng về bi kịch bị xã hội tha hóa, từ một người lương thiện thành kẻ lưu manh. Mình thích cách Nam Cao miêu tả chân thực, nhưng phân tích chi tiết hơi khó. Môn này giúp mình hiểu sâu hơn về con người và xã hội. Đánh giá 5 sao vì quá hay!', 5, '2025-03-01 11:30:00', '2025-03-01 11:30:00'),
                                                                                         (25, 3, N'Ngữ pháp câu điều kiện', N'Học câu điều kiện trong tiếng Anh, mình thấy rối vì nhiều loại quá. Thầy giải thích rõ, nhưng áp dụng vào bài tập thì mình hay nhầm lẫn giữa “if” loại 1 và loại 2. Nghe bài hội thoại thì ổn hơn, nhưng viết câu dài vẫn chưa trôi chảy. Đánh giá 2 sao vì mình cần luyện nhiều hơn.', 2, '2025-03-01 14:00:00', '2025-03-01 14:00:00'),
                                                                                         (26, 4, N'Hịch tướng sĩ', N'Học Hịch tướng sĩ của Trần Hưng Đạo trong Lịch sử, mình cảm nhận được khí thế hào hùng. Thầy đọc từng câu, phân tích ý nghĩa, làm mình tự hào về tinh thần yêu nước. Dù văn bản cổ hơi khó hiểu, nhưng nghe kể về chiến thắng Bạch Đằng thì rất đã. Đánh giá 5 sao vì quá ấn tượng!', 5, '2025-03-01 16:15:00', '2025-03-01 16:15:00'),
                                                                                         (27, 7, N'Bảng tuần hoàn', N'Hôm nay môn Hóa học học bảng tuần hoàn. Mình thấy thú vị vì hiểu cách các nguyên tố sắp xếp, nhưng nhớ hết vị trí thì khó quá. Thầy cho mẹo học thuộc, mình thử áp dụng mà vẫn quên vài phần. Thực hành thì vui hơn, mong được làm thí nghiệm nhiều. Đánh giá 4 sao vì còn cần cố gắng.', 4, '2025-03-01 18:45:00', '2025-03-01 18:45:00'),
                                                                                         (28, 1, N'Đạo hàm cơ bản', N'Học đạo hàm trong Toán, mình thấy hơi rối với các công thức. Thầy giảng về cách tính giới hạn, minh họa bằng đồ thị, nhưng mình vẫn chậm hiểu. Làm bài tập thì dần quen, cảm giác giải được bài khó rất thích. Môn này cần kiên nhẫn, mình đánh giá 4 sao vì đang tiến bộ.', 4, '2025-03-02 08:30:00', '2025-03-02 08:30:00'),
                                                                                         (29, 2, N'Tú Xương hài hước', N'Đọc thơ Trần Tế Xương trong Ngữ văn, mình cười đau bụng vì sự châm biếm. Thầy phân tích cách ông phê phán xã hội, vừa sâu sắc vừa thú vị. Mình thích kiểu văn học này, dù nhớ hết bài hơi khó. Môn này làm mình yêu thơ hơn. Đánh giá 5 sao vì quá xuất sắc!', 5, '2025-03-02 10:00:00', '2025-03-02 10:00:00'),
                                                                                         (30, 3, N'Nghe tiếng Anh', N'Học nghe tiếng Anh, bài hôm nay nhanh quá, mình không bắt kịp. Thầy cho nghe đoạn hội thoại về du lịch, từ vựng lạ nhiều, phát âm cũng khó. Mình ghi chú lại để học, nhưng vẫn thấy thiếu tự tin khi trả lời câu hỏi. Đánh giá 3 sao vì cần cải thiện kỹ năng nghe.', 3, '2025-03-02 13:20:00', '2025-03-02 13:20:00'),
                                                                                         (31, 4, N'Bình Ngô đại cáo', N'Học Bình Ngô đại cáo trong Lịch sử, mình xúc động với tinh thần dân tộc. Thầy giảng về Nguyễn Trãi, cách ông viết hùng hồn, khẳng định độc lập. Văn bản hơi dài, nhưng nghe kể về chiến thắng Đông Đô thì rất tự hào. Đánh giá 5 sao vì quá ý nghĩa!', 5, '2025-03-02 15:50:00', '2025-03-02 15:50:00'),
                                                                                         (32, 7, N'Hóa hữu cơ nhập môn', N'Học hóa hữu cơ trong Hóa học, mình thấy khó vì nhiều công thức mới. Thầy giải thích về hydrocacbon, nhưng mình chưa phân biệt rõ các hợp chất. Thí nghiệm thì thú vị, thấy phản ứng cháy rất ấn tượng. Mình đánh giá 3 sao vì cần ôn lại lý thuyết nhiều hơn.', 3, '2025-03-02 18:10:00', '2025-03-02 18:10:00'),
                                                                                         (33, 1, N'Tích phân đầu tiên', N'Học tích phân trong Toán, mình thấy thử thách vì công thức phức tạp. Thầy hướng dẫn từng bước, vẽ đồ thị minh họa, nhưng mình vẫn nhầm khi tính. Làm bài tập thì dần quen, cảm giác hiểu được rất tuyệt. Môn này cần tập trung cao, đánh giá 4 sao vì mình đang tiến bộ.', 4, '2025-03-03 09:15:00', '2025-03-03 09:15:00'),
                                                                                         (34, 2, N'Lục Vân Tiên', N'Đọc Lục Vân Tiên trong Ngữ văn, mình thích tinh thần nhân nghĩa của nhân vật. Thầy phân tích từng đoạn, kể về lòng trung hiếu, làm mình cảm động. Văn vần hơi khó nhớ, nhưng câu chuyện rất ý nghĩa. Môn này giúp mình hiểu thêm về giá trị sống. Đánh giá 5 sao!', 5, '2025-03-03 11:40:00', '2025-03-03 11:40:00'),
                                                                                         (35, 3, N'Viết luận tiếng Anh', N'Học viết luận tiếng Anh về giáo dục, mình thấy khó vì thiếu từ vựng. Thầy hướng dẫn cấu trúc bài, nhưng mình viết còn lủng củng, ý chưa mạch lạc. Đọc mẫu thì ổn, nhưng tự làm thì chậm. Đánh giá 2 sao vì cần cải thiện nhiều để viết tốt hơn.', 2, '2025-03-03 14:00:00', '2025-03-03 14:00:00'),
                                                                                         (36, 4, N'Khởi nghĩa Tây Sơn', N'Học khởi nghĩa Tây Sơn trong Lịch sử, mình khâm phục Nguyễn Huệ. Thầy kể về trận Ngọc Hồi, cách ông đánh tan quân Thanh, nghe rất hào hùng. Dù ngày tháng hơi rối, nhưng câu chuyện làm mình tự hào. Đánh giá 5 sao vì quá ấn tượng và ý nghĩa!', 5, '2025-03-03 16:30:00', '2025-03-03 16:30:00'),
                                                                                         (37, 7, N'Phản ứng oxi hóa', N'Học phản ứng oxi hóa trong Hóa học, mình thấy lý thuyết hơi khô khan. Thầy giải thích về sự mất và nhận electron, nhưng mình chưa nắm rõ. Thí nghiệm thì thú vị, thấy màu dung dịch thay đổi rất đẹp. Đánh giá 4 sao vì thực hành cứu vớt phần lý thuyết.', 4, '2025-03-03 18:45:00', '2025-03-03 18:45:00'),
                                                                                         (38, 1, N'Hàm số lượng giác', N'Học hàm số lượng giác trong Toán, mình thấy đồ thị rất đẹp. Thầy giảng về sin, cos, nhưng mình hay nhầm công thức. Làm bài tập thì dần quen, cảm giác giải được bài khó rất thích. Môn này cần kiên nhẫn, đánh giá 4 sao vì mình đang tiến bộ.', 4, '2025-03-04 08:20:00', '2025-03-04 08:20:00'),
                                                                                         (39, 2, N'Thơ Xuân Quỳnh', N'Học thơ Xuân Quỳnh trong Ngữ văn, mình yêu bài “Sóng”. Thầy phân tích tình yêu mãnh liệt, cách dùng từ tinh tế, làm mình rung động. Mình thích môn này vì gợi cảm xúc, dù phân tích sâu hơi khó. Đánh giá 5 sao vì thơ quá đẹp và ý nghĩa!', 5, '2025-03-04 10:50:00', '2025-03-04 10:50:00'),
                                                                                         (40, 3, N'Nói tiếng Anh', N'Học nói tiếng Anh, mình hồi hộp khi thực hành nhóm. Thầy cho chủ đề về sở thích, nhưng mình ấp úng vì thiếu từ vựng. Nghe bạn nói trôi chảy thì ngưỡng mộ, mình cần luyện thêm. Đánh giá 3 sao vì kỹ năng nói của mình chưa tốt lắm.', 3, '2025-03-04 13:15:00', '2025-03-04 13:15:00'),
                                                                                         (41, 4, N'Nhà Trần chống Nguyên', N'Học nhà Trần chống Nguyên trong Lịch sử, mình thấy tự hào. Thầy kể về trận Bạch Đằng, cách Trần Hưng Đạo dùng cọc gỗ đánh địch, rất thông minh. Dù ngày tháng khó nhớ, nhưng chiến thắng này quá tuyệt. Đánh giá 5 sao vì quá hào hùng!', 5, '2025-03-04 15:30:00', '2025-03-04 15:30:00'),
                                                                                         (42, 7, N'Acid và bazơ', N'Học acid và bazơ trong Hóa học, mình thích thí nghiệm đổi màu quỳ. Thầy giải thích về pH, cách phản ứng xảy ra, nhưng lý thuyết hơi dài dòng. Mình hiểu hơn khi làm thực hành, thấy môn này thực tế. Đánh giá 4 sao vì thú vị mà hơi khó.', 4, '2025-03-04 17:45:00', '2025-03-04 17:45:00'),
                                                                                         (43, 1, N'Tổ hợp xác suất', N'Học tổ hợp xác suất trong Toán, mình thấy thú vị nhưng khó. Thầy giảng về cách tính hoán vị, tổ hợp, nhưng mình hay nhầm công thức. Làm bài tập thì dần quen, cảm giác giải được rất đã. Đánh giá 3 sao vì mình cần luyện thêm nhiều.', 3, '2025-03-05 09:00:00', '2025-03-05 09:00:00'),
                                                                                         (44, 2, N'Tuyên ngôn độc lập', N'Học Tuyên ngôn độc lập trong Ngữ văn, mình xúc động với lời văn của Bác Hồ. Thầy phân tích ý nghĩa lịch sử, cách lập luận sắc bén, làm mình tự hào. Văn bản ngắn nhưng sâu sắc, mình thích môn này. Đánh giá 5 sao vì quá ý nghĩa!', 5, '2025-03-05 11:20:00', '2025-03-05 11:20:00'),
                                                                                         (45, 3, N'Đọc hiểu tiếng Anh', N'Học đọc hiểu tiếng Anh, bài về công nghệ hơi dài. Thầy hướng dẫn tìm ý chính, nhưng mình gặp khó với từ vựng chuyên ngành. Đọc chậm nên chưa kịp làm hết câu hỏi. Đánh giá 3 sao vì cần cải thiện tốc độ và vốn từ.', 3, '2025-03-05 13:40:00', '2025-03-05 13:40:00'),
                                                                                         (46, 4, N'Lý Thường Kiệt', N'Học Lý Thường Kiệt trong Lịch sử, mình ấn tượng với bài thơ Nam quốc sơn hà. Thầy kể về trận Như Nguyệt, tinh thần chống Tống, làm mình tự hào. Dù ngày tháng hơi rối, nhưng câu chuyện quá hay. Đánh giá 5 sao vì rất ý nghĩa!', 5, '2025-03-05 16:00:00', '2025-03-05 16:00:00'),
                                                                                         (47, 7, N'Kim loại kiềm', N'Học kim loại kiềm trong Hóa học, mình thích thí nghiệm với natri. Thầy cho xem phản ứng với nước, rất ấn tượng khi thấy cháy. Lý thuyết về tính chất thì hơi khô, mình cần ôn lại. Đánh giá 4 sao vì thực hành làm mình hứng thú.', 4, '2025-03-05 18:15:00', '2025-03-05 18:15:00'),
                                                                                         (48, 1, N'Phương trình lượng giác', N'Học phương trình lượng giác trong Toán, mình thấy khó vì nhiều công thức. Thầy giảng về cách biến đổi, nhưng mình vẫn nhầm lẫn. Làm bài tập thì dần hiểu, cảm giác giải được rất thích. Đánh giá 4 sao vì mình đang tiến bộ từ từ.', 4, '2025-03-06 08:30:00', '2025-03-06 08:30:00'),
                                                                                         (49, 2, N'Văn tế nghĩa sĩ Cần Giuộc', N'Học Văn tế nghĩa sĩ Cần Giuộc trong Ngữ văn, mình xúc động với lòng yêu nước. Thầy phân tích hình ảnh nông dân anh hùng, văn phong bi tráng, làm mình tự hào. Dù hơi khó nhớ, nhưng bài này rất hay. Đánh giá 5 sao vì quá tuyệt vời!', 5, '2025-03-06 10:45:00', '2025-03-06 10:45:00'),
                                                                                         (50, 3, N'Ngữ pháp thì hoàn thành', N'Học thì hoàn thành trong tiếng Anh, mình rối vì cách dùng. Thầy giải thích rõ, nhưng mình hay nhầm với thì quá khứ. Bài tập thì ổn hơn, nhưng viết câu dài vẫn khó. Đánh giá 3 sao vì cần luyện thêm để nắm chắc.', 3, '2025-03-06 13:00:00', '2025-03-06 13:00:00'),
                                                                                         (51, 4, N'Phong trào Cần Vương', N'Học phong trào Cần Vương trong Lịch sử, mình khâm phục tinh thần chống Pháp. Thầy kể về các cuộc khởi nghĩa, sự hy sinh của nghĩa sĩ, làm mình xúc động. Dù ngày tháng hơi nhiều, nhưng câu chuyện rất ý nghĩa. Đánh giá 5 sao vì quá hào hùng!', 5, '2025-03-06 15:20:00', '2025-03-06 15:20:00'),
                                                                                         (52, 7, N'Hợp chất hữu cơ', N'Học hợp chất hữu cơ trong Hóa học, mình thấy khó vì nhiều nhóm chức. Thầy giải thích về ancol, axit, nhưng mình chưa phân biệt rõ. Thí nghiệm thì thú vị, mùi cồn hơi lạ. Đánh giá 3 sao vì cần ôn lại để hiểu sâu hơn.', 3, '2025-03-06 17:40:00', '2025-03-06 17:40:00'),
                                                                                         (53, 1, N'Bất đẳng thức', N'Học bất đẳng thức trong Toán, mình thấy thú vị khi chứng minh. Thầy giảng về Cauchy, cách làm bài, nhưng mình vẫn chậm ở bước biến đổi. Làm bài tập thì dần quen, cảm giác giải được rất đã. Đánh giá 4 sao vì mình đang tiến bộ.', 4, '2025-03-07 09:10:00', '2025-03-07 09:10:00'),
                                                                                         (54, 2, N'Thơ Nguyễn Đình Chiểu', N'Học thơ Nguyễn Đình Chiểu trong Ngữ văn, mình thích tinh thần yêu nước. Thầy phân tích bài Văn tế, cách dùng từ mạnh mẽ, làm mình xúc động. Dù văn cổ hơi khó, nhưng ý nghĩa sâu sắc. Đánh giá 5 sao vì quá hay và cảm hứng!', 5, '2025-03-07 11:30:00', '2025-03-07 11:30:00'),
                                                                                         (55, 3, N'Bài nghe về môi trường', N'Học nghe tiếng Anh về môi trường, mình thấy khó vì tốc độ nhanh. Thầy cho nghe đoạn hội thoại, từ vựng mới nhiều, mình không theo kịp. Ghi chú lại thì ổn hơn, nhưng cần luyện thêm. Đánh giá 3 sao vì kỹ năng nghe của mình còn yếu.', 3, '2025-03-07 13:50:00', '2025-03-07 13:50:00'),
                                                                                         (56, 4, N'Chiến tranh thế giới', N'Học Chiến tranh thế giới trong Lịch sử, mình thấy hấp dẫn nhưng buồn. Thầy kể về Thế chiến II, sự tàn khốc của chiến tranh, làm mình suy ngẫm. Dù ngày tháng hơi rối, nhưng bài học rất ý nghĩa. Đánh giá 5 sao vì quá ấn tượng!', 5, '2025-03-07 16:10:00', '2025-03-07 16:10:00'),
                                                                                         (57, 7, N'Phản ứng cháy', N'Học phản ứng cháy trong Hóa học, mình thích thí nghiệm với oxi. Thầy giải thích về nhiệt lượng, nhưng mình chưa hiểu hết công thức. Thực hành thì vui, thấy ngọn lửa đổi màu rất đẹp. Đánh giá 4 sao vì thực hành làm mình hứng thú hơn.', 4, '2025-03-07 18:30:00', '2025-03-07 18:30:00'),
                                                                                         (58, 1, N'Mặt cầu trong không gian', N'Học mặt cầu trong Toán, mình thấy khó tưởng tượng. Thầy vẽ hình, giải thích phương trình, nhưng mình vẫn nhầm khi tính. Làm bài tập thì dần quen, cảm giác hiểu được rất thích. Đánh giá 3 sao vì mình cần luyện thêm nhiều hơn.', 3, '2025-03-08 08:40:00', '2025-03-08 08:40:00'),
                                                                                         (59, 2, N'Thơ Hồ Xuân Hương', N'Học thơ Hồ Xuân Hương trong Ngữ văn, mình thích sự dí dỏm. Thầy phân tích bài Bánh trôi nước, cách bà nói về phụ nữ, vừa hài vừa sâu sắc. Mình yêu môn này vì sự sáng tạo. Đánh giá 5 sao vì quá tuyệt vời và thú vị!', 5, '2025-03-08 10:50:00', '2025-03-08 10:50:00'),
                                                                                         (60, 3, N'Viết email tiếng Anh', N'Học viết email tiếng Anh, mình thấy khó vì cấu trúc lạ. Thầy hướng dẫn cách mở đầu, kết thúc, nhưng mình viết còn cứng, từ vựng chưa phong phú. Đọc mẫu thì ổn, nhưng tự làm thì chậm. Đánh giá 3 sao vì cần cải thiện nhiều.', 3, '2025-03-08 13:15:00', '2025-03-08 13:15:00'),
                                                                                         (61, 4, N'Văn minh Ấn Độ', N'Học văn minh Ấn Độ trong Lịch sử, mình thấy thú vị với kiến trúc đền tháp. Thầy kể về sông Hằng, văn hóa cổ đại, làm mình tò mò. Dù ngày tháng hơi nhiều, nhưng bài học rất hay. Đánh giá 5 sao vì mở mang kiến thức!', 5, '2025-03-08 15:30:00', '2025-03-08 15:30:00'),
                                                                                         (62, 7, N'Dung dịch hóa học', N'Học dung dịch trong Hóa học, mình thích thí nghiệm pha loãng. Thầy giải thích về nồng độ mol, nhưng mình chưa hiểu hết cách tính. Thực hành thì vui, thấy màu dung dịch thay đổi rất đẹp. Đánh giá 4 sao vì thực hành làm mình hứng thú.', 4, '2025-03-08 17:45:00', '2025-03-08 17:45:00'),
                                                                                         (63, 1, N'Lượng giác nâng cao', N'Học lượng giác nâng cao trong Toán, mình thấy thử thách vì công thức dài. Thầy giảng về cách giải, nhưng mình hay nhầm biến đổi. Làm bài tập thì dần quen, cảm giác giải được rất thích. Đánh giá 4 sao vì mình đang tiến bộ từ từ.', 4, '2025-03-09 09:00:00', '2025-03-09 09:00:00'),
                                                                                         (64, 2, N'Thơ Tố Hữu', N'Học thơ Tố Hữu trong Ngữ văn, mình thích bài Việt Bắc. Thầy phân tích tình yêu quê hương, cách dùng từ mượt mà, làm mình xúc động. Mình yêu môn này vì gợi cảm hứng. Đánh giá 5 sao vì quá hay và cảm xúc!', 5, '2025-03-09 11:20:00', '2025-03-09 11:20:00'),
                                                                                         (65, 3, N'Bài đọc về sức khỏe', N'Học đọc tiếng Anh về sức khỏe, mình thấy từ vựng mới nhiều. Thầy hướng dẫn cách đoán nghĩa, nhưng mình đọc chậm, không kịp làm hết câu hỏi. Ghi chú lại thì ổn hơn, nhưng cần luyện thêm. Đánh giá 3 sao vì kỹ năng đọc còn yếu.', 3, '2025-03-09 13:40:00', '2025-03-09 13:40:00'),
                                                                                         (66, 4, N'Văn minh Ai Cập', N'Học văn minh Ai Cập trong Lịch sử, mình ấn tượng với kim tự tháp. Thầy kể về Pharaoh, cách xây dựng kỳ diệu, làm mình kinh ngạc. Dù ngày tháng hơi rối, nhưng bài học rất hay. Đánh giá 5 sao vì mở mang tầm hiểu biết!', 5, '2025-03-09 16:00:00', '2025-03-09 16:00:00'),
                                                                                         (67, 7, N'Phản ứng trung hòa', N'Học phản ứng trung hòa trong Hóa học, mình thích thí nghiệm với axit và bazơ. Thầy giải thích về sự cân bằng, nhưng mình chưa hiểu hết công thức. Thực hành thì vui, thấy quỳ đổi màu rất đẹp. Đánh giá 4 sao vì thực hành làm mình hứng thú.', 4, '2025-03-10 08:30:00', '2025-03-10 08:30:00');