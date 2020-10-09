package com.score_keeper.repository;


import com.score_keeper.models.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClubRepository extends MongoRepository<Club, String> {
    Optional<Club> findByName(String name);
}
