package com.score_keeper.controllers;

import com.score_keeper.models.Category;
import com.score_keeper.models.User;
import com.score_keeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = {"", "/"})
    public Map<String, Object> getUsers() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("data", userRepository.findAll());
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }
    }


    @GetMapping(value = "/{id}")
    public Map<String, Object> getUserById(@PathVariable("id") String id){
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                response.put("data", userOptional.get());
                response.put("message", "User found");
                response.put("success", true);
            } else {
                response.put("message", "User not found with id " + id);
                response.put("success", false);
            }
            return response;
        }catch (Exception e){
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public Map<String, Object> updateUser(@PathVariable("id") String id,@Valid @RequestBody User user) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                user.setId(id);
                userRepository.save(user);
                response.put("message", "Successfully update");
                response.put("success", true);
            } else {
                response.put("message", "User not found with id " + id);
                response.put("success", false);
            }
            return response;
        } catch (
                Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/block/{id}")
    public Map<String, Object> blockUser(@PathVariable("id") String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                userOptional.get().setId(id);
                userOptional.get().setActive(false);
                userRepository.save(userOptional.get());
                response.put("message", "User blocked");
                response.put("success", true);
            } else {
                response.put("message", "User not found with id " + id);
                response.put("success", false);
            }
            return response;
        } catch (
                Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/unblock/{id}")
    public Map<String, Object> unblockUser(@PathVariable("id") String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                userOptional.get().setId(id);
                userOptional.get().setActive(true);
                userRepository.save(userOptional.get());
                response.put("message", "User unblocked");
                response.put("success", true);
            } else {
                response.put("message", "User not found with id " + id);
                response.put("success", false);
            }
            return response;
        } catch (
                Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> deleteCategory(@PathVariable("id") String id){
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if(userOptional.isPresent()){
                userRepository.deleteById(id);
                response.put("message", "Category deleted");
                response.put("success", true);
            }else {
                response.put("message", "Category not found with id " + id);
                response.put("success", false);
            }
            return response;
        }catch (Exception e){
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }

}
