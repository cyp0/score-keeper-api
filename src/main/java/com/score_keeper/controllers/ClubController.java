package com.score_keeper.controllers;


import com.score_keeper.models.Club;
import com.score_keeper.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/clubs")
public class ClubController {
    @Autowired
    ClubRepository clubRepository;

    @GetMapping(value =  {"/", ""})
    public Map<String , Object> getClubs(){
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("list", clubRepository.findAll());
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
        try {
            clubRepository.save(club);
            response.put("clubs", club);
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        }catch (Exception e){

            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }
}
