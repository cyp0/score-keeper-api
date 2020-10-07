package com.score_keeper.controllers;


import com.score_keeper.Entity.PlayerRank;
import com.score_keeper.models.Player;
import com.score_keeper.models.Score;
import com.score_keeper.models.Stage;
import com.score_keeper.models.StageRanking;
import com.score_keeper.repository.PlayerRepository;
import com.score_keeper.repository.ScoreRepository;
import com.score_keeper.repository.StageRankingRepository;
import com.score_keeper.repository.StageRepository;
import com.score_keeper.service.ScoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "api/score")
public class ScoreController {

    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    StageRepository stageRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    StageRankingRepository rankingRepository;
    @Autowired
    ScoreServiceImpl scoreService;

    @GetMapping(value = "")
    public Map<String, Object> getScores() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("scores", scoreRepository.findAll());
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
    public Map<String, Object> createClub(@Valid @RequestBody Score score) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Optional<Score> scoreOptional = scoreRepository.findByPlayer_Id(score.getPlayer().getId());
            if (scoreOptional.isPresent()) {
                response.put("message", "Player already registered");
                response.put("success", true);
                return response;
            }

            Player player = playerRepository.findById(score.getPlayer().getId()).get();
            Stage stage = stageRepository.findById(score.getStage().getId()).get();
            score.setPlayer(player);
            score.setStage(stage);
            scoreRepository.save(score);

            //Rankings
            //SOLO SI EL STAKE RANKING YA EXISTE
            Optional<StageRanking> stageRankingOptional = rankingRepository.findByStageId(score.getStage().getId());
            if (stageRankingOptional.isPresent()) {
                stageRankingOptional.get().getPlayerRanks().add(new PlayerRank(score.getPlayer().getFirstName(), score.getScore()));
                scoreService.calculateRankings(stageRankingOptional.get());
            } else {
                //PARA CREAR NUEVO STAGE RANKING
                System.out.println("Prueba");
                List<PlayerRank> playerRankList = new ArrayList<>();
                playerRankList.add(new PlayerRank(score.getPlayer().getFirstName(), score.getScore()));
                StageRanking stageRanking = new StageRanking(playerRankList);
                stageRanking.setTournament(score.getStage().getTournament());
                stageRanking.setStage(score.getStage());
                rankingRepository.save(stageRanking);

            }
            response.put("scores", score);
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
            if (data.getStrokes().size() != scoreRepository.findById(id).get().getStrokes().size()) {
                response.put("message", "No se puede introducir mas hoyos de los que ya existen");
                response.put("success", false);
                return response;
            } else {

                List<Score> scoreList = scoreRepository.getAllByStage_Id(id);
                Player player = playerRepository.findById(data.getPlayer().getId()).get();
                Stage stage = stageRepository.findById(data.getStage().getId()).get();
                data.setPlayer(player);
                data.setStage(stage);
                data.setId(id);
                scoreRepository.save(data);
//                scoreService.calculateRankings(scoreList);
//                StageRanking stageRanking = new StageRanking(scoreService.calculateRankings(scoreList));
//                stageRanking.setTournament(data.getStage().getTournament());
//                stageRanking.setStage(data.getStage());
//                rankingRepository.save(stageRanking);
                Optional<StageRanking> stageRanking = rankingRepository.findByStageId(data.getStage().getId());
                stageRanking.get().getPlayerRanks().forEach(x ->{
                    if(x.getName().equals(player.getFirstName())){
                        x.setStrikes(data.getScore());
                        System.out.println("Hola");
                    }
                });
                System.out.println(data.getPlayer().getLastName());
                scoreService.calculateRankings(stageRanking.get());


                response.put("message", "Successful update");
                response.put("success", true);
                return response;
            }
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }
}
