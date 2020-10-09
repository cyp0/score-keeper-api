package com.score_keeper.controllers;


import com.score_keeper.Entity.PlayerRank;
import com.score_keeper.models.*;
import com.score_keeper.repository.*;
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
    @Autowired
    GlobalRankingRepository globalRankingRepository;

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
            Stage stage = stageRepository.findById(score.getStage().getId()).get();
            if (stage.isBlocked() || stage.getTournament().isBlocked()) {
                response.put("message", "Stage is blocked");
                response.put("success", true);
                return response;
            }
            Optional<Score> scoreOptional = scoreRepository.findByStageId_AndPlayerId(score.getStage().getId(), score.getPlayer().getId());
            if (scoreOptional.isPresent()) {
                response.put("message", "Player already registered");
                response.put("success", false);
                return response;
            }

            Player player = playerRepository.findById(score.getPlayer().getId()).get();
            score.setPlayer(player);
            score.setStage(stage);
            scoreRepository.save(score);

            //Rankings
            //SOLO SI EL STAKE RANKING YA EXISTE
            Optional<StageRanking> stageRankingOptional = rankingRepository.findByStageId(score.getStage().getId());
            Optional<GlobalRanking> globalRankingOptional = globalRankingRepository.findByTournamentId(score.getStage().getTournament().getId());
            if (stageRankingOptional.isPresent()) {
                PlayerRank playerRank = new PlayerRank(score.getPlayer(), score.getScore());
                stageRankingOptional.get().getPlayerRanks().add(playerRank);

//                globalRankingOptional.get().getRankList().add(new PlayerRank(score.getPlayer(), score.getScore()));
                scoreService.calculateRankings(stageRankingOptional.get(), playerRank, globalRankingOptional.get());
            } else {
                //PARA CREAR NUEVO STAGE RANKING
                List<PlayerRank> playerRankList = new ArrayList<>();
                PlayerRank playerRank = new PlayerRank(score.getPlayer(), score.getScore());
                playerRank.setPoints(10);
                playerRankList.add(playerRank);
                StageRanking stageRanking = new StageRanking(playerRankList);
                stageRanking.setTournament(score.getStage().getTournament());
                stageRanking.setStage(score.getStage());
                rankingRepository.save(stageRanking);
//CREO QUE HABRA PROBLEMAS CON ESTO SI EL STAGE RANKING ES NUEVO, 8/10/10 7:06pm
                scoreService.calculateGlobalRankings(stageRanking, globalRankingOptional.get(), playerRank);

                //Global Ranking
//                if (!globalRankingOptional.isPresent()) {
//                    List<PlayerRank> globalRank = new ArrayList<>();
//                    GlobalRanking globalRanking = new GlobalRanking(globalRank);
//                    globalRanking.setTournament(score.getStage().getTournament());
//
//                    globalRankingRepository.save(globalRanking);
//                }
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
            Stage stage = stageRepository.findById(data.getStage().getId()).get();
            if(stage.isBlocked() || stage.getTournament().isBlocked()){
                response.put("message", "Stage is blocked");
                response.put("success", true);
                return response;
            }
            if (data.getStrokes().size() != scoreRepository.findById(id).get().getStrokes().size()) {
                response.put("message", "No se puede introducir mas hoyos de los que ya existen");
                response.put("success", false);
                return response;
            } else {

                List<Score> scoreList = scoreRepository.getAllByStage_Id(id);
                Player player = playerRepository.findById(data.getPlayer().getId()).get();
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
                stageRanking.get().getPlayerRanks().forEach(x -> {
                    if (x.getPlayer().getId().equals(player.getId())) {
                        x.setStrikes(data.getScore());
                        System.out.println("Hola");
                    }
                });
                //AGREGARLE EL GLOBAL RANKING REPOSITORY O VER OTRA SOLUCION 8/10/2020
//                scoreService.calculateRankings(stageRanking.get());


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

    @DeleteMapping(value = "/{id}")
    public Map<String, Object> deleteScore(@PathVariable("id") String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Score> scoreOptional = scoreRepository.findById(id);
            if (scoreOptional.isPresent()) {
                stageRepository.deleteById(id);
                response.put("message", "Score deleted");
                response.put("success", true);
            } else {
                response.put("message", "Score not found with id " + id);
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
