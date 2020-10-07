package com.score_keeper.service;

import com.score_keeper.Entity.PlayerRank;
import com.score_keeper.models.Score;
import com.score_keeper.models.StageRanking;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface ScoreService {
    @NotNull List<Score> getAllScoreByStageID(String id);
//    @NotNull List<PlayerRank> calculateRankings(List<Score> scoreList);
        public void calculateRankings(StageRanking stageRanking);
}
