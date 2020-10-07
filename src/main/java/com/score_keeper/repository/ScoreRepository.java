package com.score_keeper.repository;

import com.score_keeper.models.Player;
import com.score_keeper.models.Score;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ScoreRepository extends MongoRepository<Score, String> {
    List<Score> getAllByStage_Id(String id);
    Optional<Score> findByPlayer_Id(String name);
}
