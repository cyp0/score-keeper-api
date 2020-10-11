package com.score_keeper.controllers;


import com.score_keeper.entity.PlayerRank;
import com.score_keeper.models.*;
import com.score_keeper.repository.*;
import com.score_keeper.service.ScoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/scores")
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

    @GetMapping(value = {"/", ""})
    public Map<String, Object> getScores() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("data", scoreRepository.findAll());
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping(value = {"/", ""})
    public Map<String, Object> createScore(@Valid @RequestBody Score score) {
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

            AtomicBoolean surpassLimit = new AtomicBoolean(false);
            score.getStrokes().forEach(x -> {
                if(x.getStroke() > 10 || 0 >= x.getStroke()){
                    surpassLimit.set(true);
                }
            });
            if(surpassLimit.get()){
                response.put("message", "Stroke must be between 1 and 9");
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
                scoreService.calculateRankings(stageRankingOptional.get(), globalRankingOptional.get());
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

                scoreService.calculateGlobalRankings(globalRankingOptional.get());

            }
            response.put("data", score);
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @GetMapping(value = "/stage/{stage}/player/{player}")
    public Map<String, Object> getScoreByStageAndPlayer(@PathVariable("stage") String stageID , @PathVariable("player") String playerID) {

        HashMap<String, Object> response = new HashMap<String, Object>();

        try {
            Optional<Score> score = scoreRepository.findByStageId_AndPlayerId(stageID, playerID);
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

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Map<String, Object> updateScore(@PathVariable("id") String id, @RequestBody Score data) {

        HashMap<String, Object> response = new HashMap<String, Object>();

        try {
            if(scoreRepository.findById(id).isEmpty()){
                response.put("message", "Score not found with id " + id);
                response.put("success", false);
                return response;
            }
            Stage stage = stageRepository.findById(data.getStage().getId()).get();
            if (stage.isBlocked() || stage.getTournament().isBlocked()) {
                response.put("message", "Stage is blocked");
                response.put("success", true);
                return response;
            }
            if (data.getStrokes().size() != scoreRepository.findById(id).get().getStrokes().size()) {
                response.put("message", "You can only introduce 9 holes");
                response.put("success", false);
                return response;
            } else {

                List<Score> scoreList = scoreRepository.getAllByStage_Id(id);
                Player player = playerRepository.findById(data.getPlayer().getId()).get();
                data.setPlayer(player);
                data.setStage(stage);
                data.setId(id);
                scoreRepository.save(data);
                Optional<StageRanking> stageRanking = rankingRepository.findByStageId(data.getStage().getId());
                Optional<GlobalRanking> globalRankingOptional = globalRankingRepository.findByTournamentId(data.getStage().getTournament().getId());

                stageRanking.get().getPlayerRanks().forEach(x -> {
                    if (x.getPlayer().getId().equals(player.getId())) {
                        x.setStrikes(data.getScore());
                    }
                });
                scoreService.calculateRankings(stageRanking.get(),globalRankingOptional.get());
                response.put("message", "Score updated");
                response.put("success", true);
                return response;
            }
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> deleteScore(@PathVariable("id") String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Score> scoreOptional = scoreRepository.findById(id);

            if (!scoreOptional.isPresent()) {
                response.put("message", "Score not found with id " + id);
                response.put("success", false);
                return response;
            }
            Stage stage = scoreOptional.get().getStage();
            if (stage.isBlocked() || stage.getTournament().isBlocked()) {
                response.put("message", "Stage is blocked");
            } else {
                stageRepository.deleteById(id);
                response.put("message", "Score deleted");
            }
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }
}
