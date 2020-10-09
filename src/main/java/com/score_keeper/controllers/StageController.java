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
import java.util.Optional;

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
            if (tournament.isBlocked() || data.isBlocked()) {
                response.put("message", "Stage is blocked");
                response.put("success", true);
                return response;
            }
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

    @GetMapping(value = "/{id}")
    public Map<String, Object> getStageById(@PathVariable("id") String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Stage> stageOptional = stageRepository.findById(id);
            if (stageOptional.isPresent()) {
                response.put("data", stageOptional.get());
                response.put("message", "Stage found");
                response.put("success", true);
            } else {
                response.put("message", "Stage not found with id " + id);
                response.put("success", false);
            }
            return response;
        } catch (Exception e) {
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PutMapping(value = "/{id}")
    public Map<String, Object> updateStage(@PathVariable("id") String id, @Valid @RequestBody Stage stage) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            if (stageRepository.findById(id).get().getTournament().isBlocked() || stageRepository.findById(id).get().isBlocked()) {
                response.put("message", "Stage is blocked");
                response.put("success", true);
                return response;
            }
            Optional<Stage> stageOptional = stageRepository.findById(id);
            if (stageRepository.findById(id).get().getTournament().isBlocked() || stage.isBlocked()) {
                response.put("message", "Stage is blocked");
                response.put("success", true);
                return response;
            }
            if (stageOptional.isPresent()) {
                stage.setId(id);
                stageRepository.save(stage);
                response.put("message", "Successfully update");
                response.put("success", true);
            } else {
                response.put("message", "Stage not found with id " + id);
                response.put("success", false);
            }
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @DeleteMapping(value = "/{id}")
    public Map<String, Object> deleteStage(@PathVariable("id") String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Stage> stageOptional = stageRepository.findById(id);
            if (stageOptional.isPresent()) {
                stageRepository.deleteById(id);
                response.put("message", "Stage deleted");
                response.put("success", true);
            } else {
                response.put("message", "Stage not found with id " + id);
                response.put("success", false);
            }
            return response;
        } catch (Exception e) {
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PostMapping(value = "/block/{id}")
    public Map<String, Object> blockStage(@PathVariable("id") String id){
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Stage> stageOptional = stageRepository.findById(id);
            if (stageOptional.isPresent()) {
                stageOptional.get().setBlocked(true);
                stageOptional.get().setId(id);
                stageRepository.save(stageOptional.get());
                response.put("stage", stageOptional.get());
                response.put("message", "Stage blocked");
                response.put("success", true);
            } else {
                response.put("message", "Tournament not found with id " + id);
                response.put("success", false);
            }
            return response;
        }catch (Exception e){
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PostMapping(value = "/unblock/{id}")
    public Map<String, Object> unblockStage(@PathVariable("id") String id){
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Stage> stageOptional = stageRepository.findById(id);
            if (stageOptional.isPresent()) {
                stageOptional.get().setBlocked(false);
                stageOptional.get().setId(id);
                stageRepository.save(stageOptional.get());
                response.put("stage", stageOptional.get());
                response.put("message", "Stage unblocked");
                response.put("success", true);
            } else {
                response.put("message", "Tournament not found with id " + id);
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
