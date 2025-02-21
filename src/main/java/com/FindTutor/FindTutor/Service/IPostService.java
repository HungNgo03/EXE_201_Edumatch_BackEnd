package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.PostDTO;
import com.FindTutor.FindTutor.Entity.Post;

import java.util.List;

public interface IPostService {
    Post addPost(Post post);
    List<PostDTO> getAllPost();
    List<PostDTO> findPostsByUsername(String username);
    List<PostDTO> findPostsBySubject(String subjectname);
    List<String> getAllSubject();
    List<PostDTO> findPostsBySubjectAndUsername(String subjectname, String username);
}
