package com.FindTutor.FindTutor.Controller;

import com.FindTutor.FindTutor.DTO.PostDTO;
import com.FindTutor.FindTutor.DTO.SubjectDTO;
import com.FindTutor.FindTutor.Entity.Post;
import com.FindTutor.FindTutor.Entity.Users;
import com.FindTutor.FindTutor.Response.EHttpStatus;
import com.FindTutor.FindTutor.Response.Response;
import com.FindTutor.FindTutor.Service.IPostService;
import com.FindTutor.FindTutor.Service.PostService;
import com.FindTutor.FindTutor.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/Post")
public class PostController {
    @Autowired
    private IPostService postService;

    @GetMapping("/getAllPost")
    public Response<List<PostDTO>> getAllPost(){
        return new Response<>(EHttpStatus.OK, postService.getAllPost());
    }
    @GetMapping("/getAllPost1")
    public Response<?> getAllPost1(@RequestParam(defaultValue = "0") Integer pageNo,
                                   @RequestParam(defaultValue = "5") Integer pageSize
                                   ) throws Exception {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return new Response<>(EHttpStatus.OK, postService.getAllPost1(pageable));
    }
    @PostMapping("/addPost")
    public Response<?> addPost(@RequestBody Post post) {
        try {
            Post savedPost = postService.addPost(post);
            return new Response<>(EHttpStatus.OK, savedPost);
        } catch (Exception e) {
            return new Response<>(EHttpStatus.INTERNAL_SERVER_ERROR, "Error adding post: " + e.getMessage());
        }
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
    @GetMapping("/getAllSubjectId")
    public Response<List<SubjectDTO>> getAllSubjectId(){
        return new Response<>(EHttpStatus.OK, postService.getAllSubjectId());
    }
    @GetMapping("/findPostsBySubjectAndUsername")
    public Response<List<PostDTO>> findPostsBySubjectAndUsername(@RequestParam String subjectname, @RequestParam String username) {
        List<PostDTO> postsBySubjectAndUsername = postService.findPostsBySubjectAndUsername(subjectname, username);
        return new Response<>(EHttpStatus.OK, postsBySubjectAndUsername);
    }
    @GetMapping("/getCurrentUser")
    public Response<?> getCurrentUser(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return new Response<>(EHttpStatus.UNAUTHORIZED, "User is not logged in");
        }
        return new Response<>(EHttpStatus.OK, "User is logged in");
    }
    @GetMapping("/getAllSubject2")
    public Response<List<SubjectDTO>> getAllSubject2(){
        return new Response<>(EHttpStatus.OK, postService.getAllSubject2());
    }
}
