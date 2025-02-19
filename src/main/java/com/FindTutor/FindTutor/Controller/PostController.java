package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.DTO.PostDTO;
import com.FindTutor.FindTutor.Entity.Post;
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.IPostService;
import com.FindTutor.FindTutor.Service.PostService;
import com.FindTutor.FindTutor.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/Post")
public class PostController {
    @Autowired
    private IPostService postService;
    @Autowired
    private UserService userService;
    @GetMapping("/getAllPost")
    public Response<List<PostDTO>> getAllPost(){
        return new Response<>(EHttpStatus.OK, postService.getAllPost());
    }
    @PostMapping("/addPost")
    public Response<?> addPost(@RequestBody Post post){
        return new Response<>(EHttpStatus.OK, postService.addPost(post));
    }
    @GetMapping("/findPostsByUsername")
    public Response<List<PostDTO>> findPostsByUsername(@RequestParam String username) {
        return new Response<>(EHttpStatus.OK, postService.findPostsByUsername(username));
    }

    @GetMapping("/findPostsBySubject")
    public Response<List<PostDTO>> findPostsBySubject(@RequestParam String subjectname) {
        return new Response<>(EHttpStatus.OK, postService.findPostsBySubject(subjectname));
    }
    @GetMapping("/getAllSubject")
    public Response<List<String>> getAllSubject(){
        return new Response<>(EHttpStatus.OK, postService.getAllSubject());
    }
    @GetMapping("/findPostsBySubjectAndUsername")
    public Response<List<PostDTO>> findPostsBySubjectAndUsername(@RequestParam String subjectname, @RequestParam String username) {
        List<PostDTO> postsBySubjectAndUsername = postService.findPostsBySubjectAndUsername(subjectname, username);
        return new Response<>(EHttpStatus.OK, postsBySubjectAndUsername);
    }
}
