package com.score_keeper.repository;

import com.score_keeper.models.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, String> {
    List<Player> findAllByClubId(String id);

    Optional<Player> findByClub_Id(String id);
    @NotNull Optional<Player> findByFirstNameAndLastName(String name, String lastName);
}
