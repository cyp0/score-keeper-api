package com.score_keeper.controllers;

import com.score_keeper.models.*;
import com.score_keeper.repository.ClubRepository;
import com.score_keeper.repository.StageRepository;
import com.score_keeper.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/stages")
public class StageController {
    @Autowired
    StageRepository stageRepository;
    @Autowired
    TournamentRepository tournamentRepository;
    @Autowired
    ClubRepository clubRepository;

    @GetMapping(value = {"/", ""})
    public Map<String, Object> getStages() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("list", stageRepository.findAll());
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @PostMapping(value = {"/", ""})
    public Map<String, Object> postPlayer(@Valid @RequestBody Stage data) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Tournament tournament = tournamentRepository.findById(data.getTournament().getId()).get();
            data.setTournament(tournament);
            Club club = clubRepository.findById(data.getClub().getId()).get();
            data.setClub(club);
            stageRepository.save(data);
            response.put("stage", data);
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
