package com.score_keeper.repository;

import com.score_keeper.models.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerRepository extends MongoRepository<Player, String> {
    List<Player> findByClub_Name(String name);
}
