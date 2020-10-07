package com.score_keeper.service;

import com.score_keeper.Entity.PlayerRank;
import com.score_keeper.models.Score;
import com.score_keeper.models.StageRanking;
import com.score_keeper.repository.ScoreRepository;
import com.score_keeper.repository.StageRankingRepository;
import com.score_keeper.repository.StageRepository;
import com.score_keeper.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;


//@Transactional
@Service
public class ScoreServiceImpl implements ScoreService{

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    StageRankingRepository rankingRepository;

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    StageRepository stageRepository;
    @Override
    public @NotNull List<Score> getAllScoreByStageID(String id) {
        return scoreRepository.getAllByStage_Id(id);
    }

//    @Override
//    public List<PlayerRank> calculateRankings(List<Score> scoreList) {
//        List<PlayerRank> playerRankList = new ArrayList<>();
//
//        scoreList.forEach(score -> {
//                    playerRankList.add(new PlayerRank(score.getPlayer().getFirstName(), score.getScore()));
//                }
//        );
////Ordena la lista
//        Collections.sort(playerRankList);
//        //Para Calcular puntos
////        for (int i =0; i<3 ; i++) {
////            playerRankList.get(i).setStrikes(25-i);
////            playerRankList.get(3).setStrikes(0);
////        }
//        return playerRankList;
//    }
@Override
public void calculateRankings(StageRanking stageRanking) {
    List<PlayerRank> playerRankList = stageRanking.getPlayerRanks();

//    scoreList.forEach(score -> {
//                playerRankList.add(new PlayerRank(score.getPlayer().getFirstName(), score.getScore()));
//            }
//    );
//Ordena la lista
    Collections.sort(playerRankList);
    stageRanking.setId(stageRanking.getId());
    rankingRepository.save(stageRanking);
    //Para Calcular puntos
//        for (int i =0; i<3 ; i++) {
//            playerRankList.get(i).setStrikes(25-i);
//            playerRankList.get(3).setStrikes(0);
//        }
//    return playerRankList;
}


}
