package com.score_keeper.repository;

import com.score_keeper.models.StageRanking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StageRankingRepository extends MongoRepository<StageRanking, String> {
Optional<StageRanking> findByStageId(String id);
List<StageRanking> findAllByTournamentId(String id);
List<StageRanking> findAllById(String id);
}
