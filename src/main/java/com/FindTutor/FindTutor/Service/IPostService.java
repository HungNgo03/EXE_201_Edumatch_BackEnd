package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.PostDTO;
import com.FindTutor.FindTutor.DTO.SubjectDTO;
import com.FindTutor.FindTutor.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostService {
    Post addPost(Post post);
    Page<PostDTO> getAllPost1(Pageable pageable) throws Exception;
    List<PostDTO> getAllPost();
    List<PostDTO> findPostsByUsername(String username);
    List<PostDTO> findPostsBySubject(String subjectname);
    List<String> getAllSubject();
    List<SubjectDTO> getAllSubjectId();
    List<PostDTO> findPostsBySubjectAndUsername(String subjectname, String username);
}
