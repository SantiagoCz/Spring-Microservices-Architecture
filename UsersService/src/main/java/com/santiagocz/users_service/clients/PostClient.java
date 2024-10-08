package com.santiagocz.users_service.clients;

import com.santiagocz.users_service.classes.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="posts-service")
public interface PostClient {

    @GetMapping("/posts/byUser{id}")
    public List<PostDto> getPostsByUser(@PathVariable Long id);

    @DeleteMapping("/posts/delete/user/{id}")
    public void deleteAllPostsByUser(@PathVariable Long id);

}
