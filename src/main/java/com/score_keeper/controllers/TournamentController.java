package com.score_keeper.controllers;

import com.score_keeper.Entity.PlayerRank;
import com.score_keeper.models.*;
import com.score_keeper.repository.GlobalRankingRepository;
import com.score_keeper.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/tournament")
public class TournamentController {
    @Autowired
    TournamentRepository tournamentRepository;
    @Autowired
    GlobalRankingRepository globalRankingRepository;

    @GetMapping(value = {"/", ""})
    public Map<String, Object> getTournaments() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("tournaments", tournamentRepository.findAll());
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
    public Map<String, Object> createTournament(@Valid @RequestBody Tournament tournament) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Optional<Tournament> tournamentOptional = tournamentRepository.findByName(tournament.getName());
            if (tournamentOptional.isPresent()) {
                response.put("message", tournament.getName() + " already exists");
                response.put("success", false);
                return response;
            } else {
                tournamentRepository.save(tournament);
                //Create new global ranking

                ArrayList<PlayerRank> playerRanks = new ArrayList<>();
                GlobalRanking globalRanking = new GlobalRanking(playerRanks);
                globalRanking.setTournament(tournament);
                globalRankingRepository.save(globalRanking);
                response.put("tournament", tournament);
                response.put("message", "Successful");
                response.put("success", true);
                return response;
            }
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> getTournamentById(@PathVariable("id") String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
            if (tournamentOptional.isPresent()) {
                response.put("data", tournamentOptional.get());
                response.put("message", "Tournament found");
                response.put("success", true);
            } else {
                response.put("message", "Tournament not found with id " + id);
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
    public Map<String, Object> updateTournament(@PathVariable("id") String id, @Valid @RequestBody Tournament tournament) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
            if (!tournamentOptional.isPresent()) {
                response.put("message", "Tournament not found with id " + id);
                response.put("success", false);
            }
            if (tournamentOptional.get().isBlocked()) {
                response.put("message", "Tournament is blocked");
                response.put("success", false);
                return response;
            } else {
                tournament.setId(id);
                tournamentRepository.save(tournament);
                response.put("message", "Successfully update");
                response.put("success", true);
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
    public Map<String, Object> deleteTournament(@PathVariable("id") String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
            if (tournamentOptional.isPresent()) {
                tournamentRepository.deleteById(id);
                response.put("message", "Tournament deleted");
                response.put("success", true);
            } else {
                response.put("message", "Tournament not found with id " + id);
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
    public Map<String, Object> blockTournament(@PathVariable("id") String id){
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
            if (tournamentOptional.isPresent()) {
                tournamentOptional.get().setBlocked(true);
                tournamentOptional.get().setId(id);
                tournamentRepository.save( tournamentOptional.get());
                response.put("tournament" , tournamentOptional.get());
                response.put("message", "Tournament blocked");
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
    public Map<String, Object> unblockTournament(@PathVariable("id") String id){
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
            if (tournamentOptional.isPresent()) {
                tournamentOptional.get().setBlocked(false);
                tournamentOptional.get().setId(id);
                tournamentRepository.save( tournamentOptional.get());
                response.put("tournament" , tournamentOptional.get());
                response.put("message", "Tournament unblocked");
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
