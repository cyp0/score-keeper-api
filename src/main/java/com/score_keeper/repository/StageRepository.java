package com.score_keeper.repository;

import com.score_keeper.models.Stage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StageRepository extends MongoRepository<Stage, String> {
    Optional<Stage> findByTournamentId(String id);
    List<Stage> findAllByTournamentId(String id);
}
