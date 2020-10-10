package com.score_keeper.service;

import com.score_keeper.entity.PlayerRank;
import com.score_keeper.models.GlobalRanking;
import com.score_keeper.models.Score;
import com.score_keeper.models.StageRanking;
import com.score_keeper.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


//@Transactional
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    StageRankingRepository rankingRepository;

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    StageRepository stageRepository;

    @Autowired
    GlobalRankingRepository globalRankingRepository;

    @Override
    public @NotNull List<Score> getAllScoreByStageID(String id) {
        return scoreRepository.getAllByStage_Id(id);
    }


    @Override
    public void calculateRankings(StageRanking stageRanking, PlayerRank playerRank, GlobalRanking globalRanking) {
        List<PlayerRank> playerRankList = stageRanking.getPlayerRanks();

        //Ordena la lista
        Collections.sort(playerRankList);
        //Para Calcular puntos
        playerRankList.forEach(x -> x.setPoints(0));
        for (int i = 0; i < playerRankList.size(); i++) {
            playerRankList.get(i).setPoints(10 - i);
        }
        stageRanking.setId(stageRanking.getId());
        rankingRepository.save(stageRanking);

//        System.out.println(playerRank.getPoints());
//        globalRanking.getRankList().add(playerRank);

//        globalRanking.getRankList().add(playerRank);


//        if(!globalRanking.getRankList().contains(playerRank)){
//            globalRanking.getRankList().add(playerRank);
//        }else {
//            int index = globalRanking.getRankList().indexOf(playerRank);
//            globalRanking.getRankList().get(index).setPoints(globalRanking.getRankList().get(index).getPoints() + playerRank.getPoints());
//        }


        calculateGlobalRankings(globalRanking);
    }

    @Override
    public void calculateGlobalRankings(GlobalRanking globalRanking) {


        List<StageRanking> stageRankings = rankingRepository.findAllByTournamentId(globalRanking.getTournament().getId());
        List<List<PlayerRank>> rankPlayers = new ArrayList<>();
        List<PlayerRank> globalRankings = new ArrayList<>();

        globalRanking.setRankList(globalRankings);
        globalRankings.addAll(stageRankings.get(0).getPlayerRanks());

        if(stageRankings.size() > 1) {
            stageRankings.remove(0);
            stageRankings.forEach(x -> rankPlayers.add(x.getPlayerRanks()));

            for (int i = 0; i < stageRankings.size(); i++) {
                rankPlayers.get(i).forEach(stage -> {
                    AtomicBoolean isFound = new AtomicBoolean(false);
                    globalRankings.forEach(global -> {
                        if (global.getPlayer().getId().equals(stage.getPlayer().getId())) {
                            global.setPoints(global.getPoints() + stage.getPoints());
                            isFound.set(true);
                        }
                    });
                    if (!isFound.get()) {
                        globalRankings.add(stage);
                    }
                });
            }
        }
        globalRanking.setId(globalRanking.getId());
        globalRanking.setRankList(globalRankings);
        globalRankingRepository.save(globalRanking);

    }


}
