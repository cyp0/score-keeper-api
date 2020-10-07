package com.score_keeper.Entity;

import com.score_keeper.models.Player;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class PlayerRank implements Comparable<PlayerRank>{


    private Player player;

    private int strikes;

    public PlayerRank(Player player, int strikes) {
        this.player = player;
        this.strikes = strikes;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    @Override
    public int compareTo(PlayerRank o) {
        return Integer.compare(getStrikes(), o.getStrikes());
    }
}
