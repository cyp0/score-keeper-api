package com.score_keeper.entity;

import com.score_keeper.models.Player;

public class PlayerRank implements Comparable<PlayerRank>{


    private Player player;
    private int strikes;
    private int points;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int compareTo(PlayerRank o) {
        return Integer.compare(getStrikes(), o.getStrikes());
    }
}
