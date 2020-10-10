package com.score_keeper.models;

import com.score_keeper.entity.PlayerRank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "stage_ranking")
public class StageRanking {
    @Id
    private String id;

    @DBRef
    @NotNull(message = "Tournament id must no be empty")
    private Tournament tournament;

    @DBRef
    @NotNull(message = "Stage id must no be empty")
    private Stage stage;

    private List<PlayerRank> playerRanks;

   public StageRanking() {
    }

    public StageRanking(List<PlayerRank> playerRanks) {
        this.playerRanks = playerRanks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public Tournament getTournament() {
//        return tournament;
//    }
//
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public List<PlayerRank> getPlayerRanks() {
        return playerRanks;
    }

    public void setPlayerRanks(List<PlayerRank> playerRanks) {
        this.playerRanks = playerRanks;
    }
}
