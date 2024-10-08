package com.santiagocz.posts_service.controllers;

import com.santiagocz.posts_service.classes.UserDto;
import com.santiagocz.posts_service.classes.Validations;
import com.santiagocz.posts_service.clients.UserClient;
import com.santiagocz.posts_service.entities.Post;
import com.santiagocz.posts_service.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserClient userClient;

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/all")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }

    @GetMapping("/byUser{id}")
    public List<Post> getPostsByUser(@PathVariable Long id) {
        System.out.println("port: " + serverPort);
        return postService.getPostsByUser(id);
    }

    @PostMapping("/save/user/{id}")
    public ResponseEntity<?> save(@RequestBody @Valid Post request,
                                  @PathVariable Long id,
                                  BindingResult result) {
        ResponseEntity<?> validationResponse = Validations.handleValidationErrors(result);
        if (validationResponse != null) {
            return validationResponse;
        }
        try {
            UserDto user = userClient.getUserById(id);
            request.setUserId(user.getId());

            postService.save(request);
            return ResponseEntity.ok("Post has been saved succesfully.");

        } catch (Exception e) {
            String message = "An internal server error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody @Valid Post request,
                                  @PathVariable Long id,
                                  BindingResult result) {

        ResponseEntity<?> validationResponse = Validations.handleValidationErrors(result);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            Post post = postService.getPostById(id);
            post.setTitle(request.getTitle());
            postService.save(post);

            return ResponseEntity.ok("Post updated successfully");

        } catch (Exception e) {
            String message = "An internal server error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @DeleteMapping("/delete/user/{id}")
    public void deleteAllPostsByUser(@PathVariable Long id){
        UserDto user = userClient.getUserById(id);
        postService.deleteAllPostsByUser(user.getId());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            Post post = postService.getPostById(id);
            postService.delete(post);

            return ResponseEntity.ok("Post has been deleted successfully");

        } catch (Exception e) {
            String message = "An internal server error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}
