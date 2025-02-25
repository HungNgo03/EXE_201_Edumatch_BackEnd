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