package com.score_keeper.repository;


import com.score_keeper.models.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubRepository extends MongoRepository<Club, String> {
    Club findByName(String name);
}
