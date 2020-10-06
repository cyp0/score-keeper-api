package com.score_keeper.repository;

import com.score_keeper.models.Stage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StageRepository extends MongoRepository<Stage, String> {
}
