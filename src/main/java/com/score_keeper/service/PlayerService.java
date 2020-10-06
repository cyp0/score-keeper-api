package com.score_keeper.service;


import com.score_keeper.models.Player;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface PlayerService {
    @NotNull List<Player> getAllPlayers();

    Optional<Player> getPlayer(String id);
    Player save(Player player);

}
