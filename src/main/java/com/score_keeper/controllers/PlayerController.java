package com.score_keeper.controllers;


import com.score_keeper.models.Category;
import com.score_keeper.models.Club;
import com.score_keeper.models.Player;
import com.score_keeper.repository.CategoryRepository;
import com.score_keeper.repository.ClubRepository;
import com.score_keeper.repository.PlayerRepository;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/players")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping(value = {"/", ""})
    public Map<String, Object> getPlayers() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("data", playerRepository.findAll());
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = {"/", ""})
    public Map<String, Object> postPlayer(@Valid @RequestBody Player player) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            if (playerRepository.findByFirstNameAndLastName(player.getFirstName(), player.getLastName()).isPresent()) {
                response.put("message", "Player is already registered in a club");
                response.put("success", false);
                return response;
            }

            LocalDate birth = new LocalDate(player.getBirth_day().getYear(), player.getBirth_day().getMonthValue(), player.getBirth_day().getDayOfMonth());
            LocalDate currentTime = LocalDate.now();
            Years age = Years.yearsBetween(birth, currentTime);
            Category category = categoryRepository.findById(player.getCategory().getId()).get();
            if (!(age.getYears() >= category.getMin_age() && age.getYears() <= category.getMax_age())) {
                response.put("message", "Player cannot belong to this category");
                response.put("success", false);
                return response;
            }
            Club club = clubRepository.findById(player.getClub().getId()).get();
            player.setClub(club);
            player.setCategory(category);
            playerRepository.save(player);
            response.put("message", "Successful");
            response.put("success", true);
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }

    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> getPlayerById(@PathVariable("id") String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Optional<Player> player = playerRepository.findById(id);
            if (player.isPresent()) {
                response.put("data", player.get());
                response.put("message", "Player found");
                response.put("success", true);
            } else {
                response.put("message", "Player not found with id " + id);
                response.put("success", false);
            }
            return response;
        } catch (Exception e) {
            response.put("message", "" + e.getMessage());
            response.put("success", false);
            return response;
        }
    }

//    @GetMapping(value = "/{firstName}/{lastName}")
//    public Map<String, Object> getPlayerByName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
//        HashMap<String, Object> response = new HashMap<String, Object>();
//        try {
//            Optional<Player> player = playerRepository.findByFirstNameAndLastName(firstName, lastName);
//            if (player.isPresent()) {
//                response.put("data", player.get());
//                response.put("message", "Player found");
//                response.put("success", true);
//            } else {
//                response.put("message", firstName + " " + lastName + " not found ");
//                response.put("success", false);
//            }
//            return response;
//        } catch (Exception e) {
//            response.put("message", "" + e.getMessage());
//            response.put("success", false);
//            return response;
//        }
//    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public Map<String, Object> updatePlayer(@PathVariable("id") String id, @Valid @RequestBody Player player) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            LocalDate birth = new LocalDate(player.getBirth_day().getYear(), player.getBirth_day().getMonthValue(), player.getBirth_day().getDayOfMonth());
            LocalDate currentTime = LocalDate.now();
            Years age = Years.yearsBetween(birth, currentTime);
            Category category = categoryRepository.findById(player.getCategory().getId()).get();
            if (!(age.getYears() >= category.getMin_age() && age.getYears() <= category.getMax_age())) {
                response.put("message", "Player cannot belong to this category");
                response.put("success", false);
                return response;
            }
            Optional<Player> playerOptional = playerRepository.findById(id);
            if (playerOptional.isPresent()) {
                player.setId(id);
                player.setClub(player.getClub());
                player.setCategory(player.getCategory());
                playerRepository.save(player);
                response.put("message", "Successful update");
                response.put("success", true);
            } else {
                response.put("message", "Player not found with id " + id);
                response.put("success", false);
            }
            return response;
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return response;
        }
    }

    //No se ocupa
//    @DeleteMapping(value = "/{id}")
//    public Map<String, Object> deletePlayer(@PathVariable("id") String id) {
//        HashMap<String, Object> response = new HashMap<String, Object>();
//        try {
//            Optional<Player> player = playerRepository.findById(id);
//            if (player.isPresent()) {
//                playerRepository.deleteById(id);
//                response.put("message", "Player deleted");
//                response.put("success", true);
//            } else {
//                response.put("message", "Player not found with id " + id);
//                response.put("success", false);
//            }
//            return response;
//        } catch (Exception e) {
//            response.put("message", "" + e.getMessage());
//            response.put("success", false);
//            return response;
//        }
//    }

//    @GetMapping(value = "/{club}")
//    public Map<String, Object> getPlayerByClub(@PathVariable("club") String club) {
//        HashMap<String, Object> response = new HashMap<String, Object>();
//
//        try {
//            List<Player> validateClub = playerRepository.findByClub_Name(club);
//            if (!validateClub.isEmpty()) {
//                response.put("message", "Successfully loaded");
//                response.put("data", validateClub);
//                response.put("success", true);
//            } else {
//                response.put("message", "Club not found");
//                response.put("data", null);
//                response.put("success", false);
//            }
//            return response;
//        } catch (Exception e) {
//            response.put("message", "" + e.getMessage());
//            response.put("success", false);
//            return response;
//        }
//    }

}
