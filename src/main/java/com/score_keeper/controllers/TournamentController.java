package com.score_keeper.controllers;

import com.score_keeper.models.Club;
import com.score_keeper.models.Stage;
import com.score_keeper.models.Tournament;
import com.score_keeper.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/tournament")
public class TournamentController {
    @Autowired
    TournamentRepository tournamentRepository;

    @GetMapping(value = {"/" , ""})
    public Map<String, Object> getTournaments(){
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("tournaments", tournamentRepository.findAll());
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        }catch (Exception e){
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PostMapping(value = {"/", ""})
    public Map<String, Object> postTournament(@Valid @RequestBody Tournament tournament) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("tournament", tournament);
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
