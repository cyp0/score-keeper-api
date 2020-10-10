package com.score_keeper.models;

import com.score_keeper.entity.PlayerRank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "global_ranking")
public class GlobalRanking {
    @Id
    private String id;

    @DBRef
    private Tournament tournament;

    private List<PlayerRank> rankList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GlobalRanking() {
    }

    public GlobalRanking(List<PlayerRank> rankList) {
        this.rankList = rankList;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public List<PlayerRank> getRankList() {
        return rankList;
    }

    public void setRankList(List<PlayerRank> rankList) {
        this.rankList = rankList;
    }
}
