package com.score_keeper.controllers;


import com.score_keeper.models.Category;
import com.score_keeper.models.Club;
import com.score_keeper.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/clubs")
public class ClubController {
    @Autowired
    ClubRepository clubRepository;

    @GetMapping(value =  {"/", ""})
    public Map<String , Object> getClubs(){
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("data", clubRepository.findAll());
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        }catch (Exception e){
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @PostMapping(value =  {"/", ""})
    public Map<String , Object> createClub(@Valid @RequestBody Club club){
        HashMap<String, Object> response = new HashMap<>();
        Optional<Club> clubOptional = clubRepository.findByName(club.getName());
        try {
            if(clubOptional.isPresent()){
                response.put("message", club.getName()+ " already exists");
                response.put("success", false);
            }else {
                clubRepository.save(club);
                response.put("message", "Successful");
                response.put("success", true);
            }
            return response;
        }catch (Exception e){
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> getClubById(@PathVariable("id") String id){
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Club> clubOptional = clubRepository.findById(id);
            if (clubOptional.isPresent()) {
                response.put("data", clubOptional.get());
                response.put("message", "Club found");
                response.put("success", true);
            } else {
                response.put("message", "Club not found with id " + id);
                response.put("success", false);
            }
            return response;
        }catch (Exception e){
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PutMapping(value = "/{id}")
    public Map<String, Object> updateClub(@PathVariable("id") String id,@Valid @RequestBody Club club) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Club> clubOptional = clubRepository.findById(id);
            if (clubOptional.isPresent()) {
                club.setId(id);
                clubRepository.save(club);
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

    @DeleteMapping(value = "/{id}")
    public Map<String, Object> deleteClub(@PathVariable("id") String id){
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Club> clubOptional = clubRepository.findById(id);
            if(clubOptional.isPresent()){
               clubRepository.deleteById(id);
                response.put("message", "Club deleted");
                response.put("success", true);
            }else {
                response.put("message", "Club not found with id " + id);
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
