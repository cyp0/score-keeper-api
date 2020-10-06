package com.score_keeper.service;


import com.score_keeper.models.Player;
import com.score_keeper.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public class PlayerServiceImpl implements PlayerService{
    private final PlayerRepository playerRepository;
    private final MongoTemplate mongoTemplate;
    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, MongoTemplate mongoTemplate) {
        this.playerRepository = playerRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public @NotNull List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> getPlayer(String id) {
        return playerRepository.findById(id);
    }

    @Override
    public Player save(Player player) {
        AggregationOperation match = Aggregation.match(Criteria.where("_id").is(player.getId()));
        AggregationOperation query = Aggregation.lookup("club", "_id", "_id", "club");
        return playerRepository.save(player);
    }


}

//    public Player save(Player player) {
////        AggregationOperation match = Aggregation.match(Criteria.where("_id").is(player.getId()));
//        AggregationOperation query = Aggregation.lookup("club", "_id", "_id", "club");
//        return playerRepository.save(player);
//    }