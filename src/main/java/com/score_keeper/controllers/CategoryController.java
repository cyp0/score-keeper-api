package com.score_keeper.controllers;

import com.score_keeper.models.Category;
import com.score_keeper.models.Club;
import com.score_keeper.models.Player;
import com.score_keeper.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping(value = {"", "/"})
    public Map<String, Object> getCategories() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("data", categoryRepository.findAll());
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = {"/", ""})
    public Map<String, Object> createCategory(@Valid @RequestBody Category category) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            categoryRepository.save(category);
            response.put("message", "Category saved");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> getCategoryById(@PathVariable("id") String id){
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if (categoryOptional.isPresent()) {
                response.put("data", categoryOptional.get());
                response.put("message", "Player found");
                response.put("success", true);
            } else {
                response.put("message", "Player not found with id " + id);
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
    public Map<String, Object> updateCategory(@PathVariable("id") String id,@Valid @RequestBody Category category) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if (categoryOptional.isPresent()) {
                category.setId(id);
                categoryRepository.save(category);
                response.put("message", "Successfully update");
                response.put("success", true);
            } else {
                response.put("message", "Category not found with id " + id);
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
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if(categoryOptional.isPresent()){
               categoryRepository.deleteById(id);
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
