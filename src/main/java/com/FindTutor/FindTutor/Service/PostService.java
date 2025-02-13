package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Post;
import com.FindTutor.FindTutor.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;
    @Override
    public Post addPost(Post post) {
        if (post.getRating() < 1 || post.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }
}
