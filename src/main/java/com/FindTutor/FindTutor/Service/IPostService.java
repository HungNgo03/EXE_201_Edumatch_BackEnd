package com.FindTutor.FindTutor.Service;

import com.FindTutor.FindTutor.Entity.Post;

import java.util.List;

public interface IPostService {
    Post addPost(Post post);
    List<Post> getAllPost();
}
