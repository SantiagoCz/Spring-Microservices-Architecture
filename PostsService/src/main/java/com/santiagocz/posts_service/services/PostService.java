package com.santiagocz.posts_service.services;

import com.santiagocz.posts_service.entities.Post;
import com.santiagocz.posts_service.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post save(Post post){
        return postRepository.save(post);
    }

    public void deleteAllPostsByUser(Long id){
        postRepository.deleteByUserId(id);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }

    public Post getPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + id));
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public List<Post> getPostsByUser(Long userId){
        return postRepository.findPostByUserId(userId);
    }
}
