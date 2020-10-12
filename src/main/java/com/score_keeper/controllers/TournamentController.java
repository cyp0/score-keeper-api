package com.score_keeper.controllers;

import com.score_keeper.entity.PlayerRank;
import com.score_keeper.models.*;
import com.score_keeper.repository.GlobalRankingRepository;
import com.score_keeper.repository.StageRepository;
import com.score_keeper.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "api/tournaments")
public class TournamentController {
    @Autowired
    TournamentRepository tournamentRepository;
    @Autowired
    GlobalRankingRepository globalRankingRepository;
    @Autowired
    StageRepository stageRepository;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = {"/", ""})
    public Map<String, Object> createTournament(@Valid @RequestBody Tournament tournament) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Optional<Tournament> tournamentOptional = tournamentRepository.findByName(tournament.getName());

                tournamentRepository.save(tournament);
                //Create new global ranking

                ArrayList<PlayerRank> playerRanks = new ArrayList<>();
                GlobalRanking globalRanking = new GlobalRanking(playerRanks);
                globalRanking.setTournament(tournament);
                globalRankingRepository.save(globalRanking);
                //Create 5 stages
                for(int i = 1; i <= tournament.getStages(); i++){
                    stageRepository.save(new Stage(tournament, i));
                }

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

    //GetStages
    @GetMapping(value = "/{tournamentID}/stages")
    public Map<String, Object> getStagesByTournamentId(@PathVariable("tournamentID") String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            List<Stage> stages = stageRepository.findAllByTournamentId(id);
            if (!stages.isEmpty()) {
                response.put("data", stages);
                response.put("message", "Stages found");
                response.put("success", true);
            } else {
                response.put("message", "Stages not found with  tournament id " + id);
                response.put("success", false);
            }
            return response;
        } catch (Exception e) {
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
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
                response.put("message", "Successfully updated");
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/block/{id}")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/unblock/{id}")
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
