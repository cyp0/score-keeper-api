package com.score_keeper.controllers;

import com.score_keeper.models.Category;
import com.score_keeper.models.StageRanking;
import com.score_keeper.repository.StageRankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/stage-rankings")
public class StageRankingController {
    @Autowired
    StageRankingRepository rankingRepository;

    @GetMapping(value = {"/", ""})
    public Map<String, Object> getRankings() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("rankings", rankingRepository.findAll());
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @GetMapping(value = "/stage/{id}")
    public Map<String, Object> getRankingOfStage(@PathVariable("id") String id) {
        rankingRepository.findByStageId(id);
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<StageRanking> stageRanking = rankingRepository.findByStageId(id);
            if (stageRanking.isPresent()) {
                response.put("data", stageRanking.get());
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



}
