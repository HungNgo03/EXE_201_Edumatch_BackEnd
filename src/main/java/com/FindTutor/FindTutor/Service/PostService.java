package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.DTO.PostDTO;
import com.FindTutor.FindTutor.DTO.SubjectDTO;
import com.FindTutor.FindTutor.Entity.Post;
import com.FindTutor.FindTutor.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;
    @Override
    public Post addPost(Post post) {

        Post newPost = new Post();
        newPost.setUserID(post.getUserID());
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setUpdatedAt(LocalDateTime.now());
        newPost.setRating(5);
        newPost.setSubjectID(post.getSubjectID());
        return postRepository.save(newPost);
    }


    @Override
    public Page<PostDTO> getAllPost1(Pageable pageable) throws Exception {
        return postRepository.getAll(pageable);
    }

    @Override
    public List<PostDTO> getAllPost() {
        return postRepository.getAllPostWithUserAndSubject();
    }

    @Override
    public List<PostDTO> findPostsByUsername(String username) {
        return postRepository.findPostsByUsername(username);
    }

    @Override
    public List<PostDTO> findPostsBySubject(String subjectname) {
        return postRepository.findPostsBySubject(subjectname);
    }

    @Override
    public List<String> getAllSubject() {
        return postRepository.getAllSubject();
    }

    @Override
    public List<SubjectDTO> getAllSubjectId() {
        return postRepository.getAllSubjectId();
    }

    @Override
    public List<PostDTO> findPostsBySubjectAndUsername(String subjectname, String username) {
        return postRepository.findPostsBySubjectAndUsername(subjectname, username);
    }
}
