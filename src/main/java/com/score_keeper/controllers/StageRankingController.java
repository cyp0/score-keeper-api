package com.score_keeper.controllers;

import com.score_keeper.repository.StageRankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/ranking")
public class StageRankingController {
    @Autowired
    StageRankingRepository rankingRepository;

    @GetMapping(value = {"/", ""})
    public Map<String, Object> getRankings(){
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

}
