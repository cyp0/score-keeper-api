package com.score_keeper.service;

import com.score_keeper.entity.PlayerRank;
import com.score_keeper.models.GlobalRanking;
import com.score_keeper.models.Score;
import com.score_keeper.models.StageRanking;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface ScoreService {
    @NotNull List<Score> getAllScoreByStageID(String id);
//    @NotNull List<PlayerRank> calculateRankings(List<Score> scoreList);
void calculateRankings(StageRanking stageRanking, PlayerRank playerRank, GlobalRanking globalRanking);
        void calculateGlobalRankings(GlobalRanking globalRanking);
}
