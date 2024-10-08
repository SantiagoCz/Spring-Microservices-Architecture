package com.santiagocz.users_service.controllers;

import com.santiagocz.users_service.classes.UserDto;
import com.santiagocz.users_service.classes.Validations;
import com.santiagocz.users_service.entities.User;
import com.santiagocz.users_service.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/posts/{id}")
    public UserDto getUserAndPosts(@PathVariable Long id){
        return userService.getUserAndPosts(id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid User request,
                                  BindingResult result) {
        ResponseEntity<?> validationResponse = Validations.handleValidationErrors(result);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            userService.save(request);
            return ResponseEntity.ok("User has been saved succesfully.");
        } catch (Exception e) {
            String message = "An internal server error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody @Valid User request,
                                  @PathVariable Long id,
                                  BindingResult result) {
        ResponseEntity<?> validationResponse = Validations.handleValidationErrors(result);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            // Find the current user by ID
            User user = userService.getUserById(id);

            // Update the user fields
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPhoneNumber(request.getPhoneNumber());

            // Save the updated user
            userService.save(user);

            return ResponseEntity.ok("User updated successfully");

        } catch (Exception e) {
            String message = "An internal server error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            User user = userService.getUserById(id);
            userService.deleteAllPostsByUser(user.getId());
            userService.delete(user);

            return ResponseEntity.ok("User and their posts has been deleted successfully");

        } catch (Exception e) {
            String message = "An internal server error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}
