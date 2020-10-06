package com.score_keeper.controllers;


import com.score_keeper.models.Category;
import com.score_keeper.models.Club;
import com.score_keeper.models.Player;
import com.score_keeper.repository.CategoryRepository;
import com.score_keeper.repository.ClubRepository;
import com.score_keeper.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/players")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    CategoryRepository categoryRepository;

    MongoOperations mongoOperations;
    @GetMapping(value = {"/", ""})
    public Map<String, Object> getPlayers() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("list", playerRepository.findAll());
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @GetMapping(value = "/{club}")
    public Map<String, Object> getPlayerByClub(@PathVariable("club") String club) {
        HashMap<String, Object> response = new HashMap<String, Object>();

        try {
            List<Player> validateClub = playerRepository.findByClub_Name(club);
            if (!validateClub.isEmpty()) {
                response.put("message", "Successful load");
                response.put("data", validateClub);
                response.put("success", true);
            } else {
                response.put("message", "Not found data");
                response.put("data", null);
                response.put("success", false);
            }
            return response;
        } catch (Exception e) {
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PostMapping(value = {"/", ""})
    public Map<String, Object> postPlayer(@Valid @RequestBody Player data) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Club club = clubRepository.findById(data.getClub().getId()).get();
            data.setClub(club);
            Category category = categoryRepository.findById(data.getCategory().getId()).get();
            data.setCategory(category);
            playerRepository.save(data);
            response.put("player", data);
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }


}
