package com.score_keeper.repository;

import com.score_keeper.models.Tournament;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TournamentRepository extends MongoRepository<Tournament, String> {
}
