package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Tutors;

import java.util.List;

public interface ITutorService {
    List<Tutors> getAllTutors();
    Tutors getTutorById(Integer id);
}
