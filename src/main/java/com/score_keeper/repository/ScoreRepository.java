package com.score_keeper.repository;

import com.score_keeper.models.Score;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScoreRepository extends MongoRepository<Score, String> {
}
