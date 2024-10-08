package com.santiagocz.users_service.services;

import com.santiagocz.users_service.classes.PostDto;
import com.santiagocz.users_service.classes.UserDto;
import com.santiagocz.users_service.clients.PostClient;
import com.santiagocz.users_service.entities.User;
import com.santiagocz.users_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostClient postClient;

    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id));
    }

    public UserDto getUserAndPosts(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id));

        List<PostDto> postDtoList = postClient.getPostsByUser(user.getId());

        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .postDtoList(postDtoList)
                .build();
    }

    public void deleteAllPostsByUser(Long id){
        postClient.deleteAllPostsByUser(id);
    }

}
