package com.score_keeper.service;

import com.score_keeper.Entity.PlayerRank;
import com.score_keeper.models.GlobalRanking;
import com.score_keeper.models.Score;
import com.score_keeper.models.StageRanking;
import com.score_keeper.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;


//@Transactional
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    StageRankingRepository rankingRepository;

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    StageRepository stageRepository;

    @Autowired
    GlobalRankingRepository globalRankingRepository;

    @Override
    public @NotNull List<Score> getAllScoreByStageID(String id) {
        return scoreRepository.getAllByStage_Id(id);
    }


    @Override
    public void calculateRankings(StageRanking stageRanking, PlayerRank playerRank, GlobalRanking globalRanking) {
        List<PlayerRank> playerRankList = stageRanking.getPlayerRanks();

        //Ordena la lista
        Collections.sort(playerRankList);
        //Para Calcular puntos
        playerRankList.forEach(x -> x.setPoints(0));
        for (int i = 0; i < playerRankList.size(); i++) {
            playerRankList.get(i).setPoints(10 - i);
        }
        stageRanking.setId(stageRanking.getId());
        rankingRepository.save(stageRanking);

//        System.out.println(playerRank.getPoints());
//        globalRanking.getRankList().add(playerRank);

//        globalRanking.getRankList().add(playerRank);


//        if(!globalRanking.getRankList().contains(playerRank)){
//            globalRanking.getRankList().add(playerRank);
//        }else {
//            int index = globalRanking.getRankList().indexOf(playerRank);
//            globalRanking.getRankList().get(index).setPoints(globalRanking.getRankList().get(index).getPoints() + playerRank.getPoints());
//        }


        calculateGlobalRankings(stageRanking,globalRanking, playerRank);
    }

    @Override
    public void calculateGlobalRankings(StageRanking stageRanking,GlobalRanking globalRanking, PlayerRank playerRank) {
        List<StageRanking> stageRankings = rankingRepository.findAllByTournamentId(globalRanking.getTournament().getId());
        stageRankings.remove(stageRanking);


        boolean detected = false;
        int difference = 0;
       //AQUI ACTUALIZO LA LISTA DE PLAYER RANK DE GLOBAL CON LOS NUEVOS PUNTOS DESPUES DE UN CAMBIO
        stageRanking.getPlayerRanks().forEach(stage -> {
            globalRanking.getRankList().forEach(global -> {
                if(stage.getPlayer().getId().equals(global.getPlayer().getId())){
                    int points = 0;
                    for(int i =0; i< stageRankings.size(); i++){
                        for (PlayerRank player: stageRankings.get(i).getPlayerRanks()) {
                            if(playerRank.getPlayer().getId().equals(player.getPlayer().getId())){
                                 points = player.getPoints();
                            }
                        }
                        if(stageRankings.size() == 1) {
                            global.setPoints((stage.getPoints() - global.getPoints()) + global.getPoints());
                        }else {
                            global.setPoints((stage.getPoints() - global.getPoints()) + global.getPoints() + points);

                        }
                    }
//                    for (PlayerRank player: stageRankings.get(i).getPlayerRanks()) {
//
//                    }

                }
            });
        });

        for (PlayerRank player : globalRanking.getRankList()) {
            if (playerRank.getPlayer().getId().equals(player.getPlayer().getId())) {
                int index = globalRanking.getRankList().indexOf(playerRank);
                detected = true;
                break;
            }
        }
        globalRanking.getRankList().forEach(x ->
        {
            System.out.println("Puntos de " + x.getPlayer().getFirstName() + " " + x.getPoints());
            System.out.println("Size of list:" + globalRanking.getRankList().size());
        });
        System.out.println("Salto");
//        globalRanking.getRankList().forEach(x -> x.setPoints(0));
        if (detected) {
            globalRanking.getRankList().forEach(x -> {
                if (playerRank.getPlayer().getId().equals(x.getPlayer().getId())) {
                    x.setPoints(x.getPoints() + playerRank.getPoints());
                }
            });
        } else {
            globalRanking.getRankList().add(playerRank);
        }

        globalRanking.getRankList().forEach(x ->
        {
            System.out.println("Puntos de " + x.getPlayer().getFirstName()+ " " + x.getPoints());
            System.out.println("Size of list:" + globalRanking.getRankList().size());
        });
        System.out.println("************************************************");
        System.out.println();
        System.out.println("Nuevo");

        globalRanking.setId(globalRanking.getId());
        globalRankingRepository.save(globalRanking);
    }


}
