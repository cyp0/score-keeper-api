package com.score_keeper.controllers;



import com.score_keeper.models.Player;
import com.score_keeper.models.Score;
import com.score_keeper.models.Stage;
import com.score_keeper.repository.PlayerRepository;
import com.score_keeper.repository.ScoreRepository;
import com.score_keeper.repository.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/score")
public class ScoreController {

    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    StageRepository stageRepository;
    @Autowired
    PlayerRepository playerRepository;


    @GetMapping(value = "")
    public Map<String, Object> getScores(){
        HashMap<String, Object> response = new HashMap<>();
        try{
            response.put("scores", scoreRepository.findAll());
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
    public Map<String , Object> createClub(@Valid @RequestBody Score score){
        HashMap<String, Object> response = new HashMap<>();
        try {
            Player player = playerRepository.findById(score.getPlayer().getId()).get();
            Stage stage = stageRepository.findById(score.getStage().getId()).get();
            score.setPlayer(player);
            score.setStage(stage);
            scoreRepository.save(score);
            response.put("scores", score);
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        }catch (Exception e){

            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> data(@PathVariable("id") String id) {

        HashMap<String, Object> response = new HashMap<String, Object>();

        try {

            Optional<Score> score = scoreRepository.findById(id);

            if (score.isPresent()) {
                response.put("message", "Successful load");
                response.put("data", score);
                response.put("success", true);
                return response;
            } else {
                response.put("message", "Not found data");
                response.put("data", null);
                response.put("success", false);
                return response;
            }

        } catch (Exception e) {
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable("id") String id,
                                      @RequestBody Score data) {

        HashMap<String, Object> response = new HashMap<String, Object>();

        try {
            data.setId(id);

            if (data.getStrokes().size() != scoreRepository.findById(id).get().getStrokes().size()) {
                response.put("message", "No se puede introducir mas hoyos de los que ya existen");
                response.put("success", false);
                return response;
            }
            else
            scoreRepository.save(data);
            response.put("message", "Successful update");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }
}
