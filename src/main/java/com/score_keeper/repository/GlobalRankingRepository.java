package com.score_keeper.repository;

import com.score_keeper.models.GlobalRanking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GlobalRankingRepository extends MongoRepository<GlobalRanking, String> {
    Optional<GlobalRanking> findByTournamentId(String id);
}
