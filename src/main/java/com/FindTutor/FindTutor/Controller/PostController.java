package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.Entity.Post;
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Post")
public class PostController {
    @Autowired
    private PostService postService;
    @GetMapping("/getAllPost")
    public Response<?> getAllPost(){
        return new Response<>(EHttpStatus.OK, postService.getAllPost());
    }
    @PostMapping("/addPost")
    public Response<?> addPost(@RequestBody Post post){
        return new Response<>(EHttpStatus.OK, postService.addPost(post));
    }
}
