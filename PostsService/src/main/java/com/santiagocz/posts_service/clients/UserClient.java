package com.santiagocz.posts_service.clients;

import com.santiagocz.posts_service.classes.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service")
public interface UserClient {

    @GetMapping("/user/{id}")
    UserDto getUserById(@PathVariable Long id);

}
