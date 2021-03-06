package com.score_keeper.controllers;

import com.score_keeper.models.GlobalRanking;
import com.score_keeper.models.Score;
import com.score_keeper.models.StageRanking;
import com.score_keeper.repository.GlobalRankingRepository;
import com.score_keeper.repository.StageRankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/global-ranking")
public class GlobalRankingController {
    @Autowired
    StageRankingRepository rankingRepository;

    @Autowired
    GlobalRankingRepository globalRankingRepository;

    @GetMapping(value = {"/", ""})
    public Map<String, Object> getGlobalRankings(){
        HashMap<String, Object> response = new HashMap<>();
        try{
            response.put("rankings", rankingRepository.findAll());
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        }catch (Exception e){
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @GetMapping(value = "/tournament/{id}")
    public Map<String, Object> getGlobalRanking(@PathVariable("id") String id){
        HashMap<String, Object> response = new HashMap<String, Object>();

        try {

            Optional<GlobalRanking> globalRanking = globalRankingRepository.findByTournamentId(id);
//            List<StageRanking> score = rankingRepository.findAllById(id);
            if (globalRanking.isPresent()) {
                response.put("message", "Tournament found");
                response.put("data", globalRanking.get());
                response.put("success", true);
                return response;
            } else {
                response.put("message", "Tournament doesn't exists");
                response.put("success", false);
                return response;
            }

        } catch (Exception e) {
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }
}
