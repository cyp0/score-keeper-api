package com.score_keeper;

import com.score_keeper.entity.PlayerRank;
import com.score_keeper.models.*;
import com.score_keeper.repository.CategoryRepository;
import com.score_keeper.repository.RoleRepository;
import com.score_keeper.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootApplication
public class SpringBootSecurityJwtMongodbApplication implements CommandLineRunner {

	@Autowired
    RoleRepository roleRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	TournamentRepository tournamentRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtMongodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
