package com.score_keeper;

import com.score_keeper.models.Category;
import com.score_keeper.models.Gender;
import com.score_keeper.models.Tournament;
import com.score_keeper.repository.CategoryRepository;
import com.score_keeper.repository.RoleRepository;
import com.score_keeper.repository.TournamentRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

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
//		Role role1 = new Role(ERole.ROLE_ADMIN);
//        Role role2 = new Role(ERole.ROLE_MODERATOR);
//        Role role3 = new Role(ERole.ROLE_USER);
//
//        roleRepository.save(role1);
//        roleRepository.save(role2);
//        roleRepository.save(role3);

//		categoryRepository.save(new Category("Categoria Juvenil", Gender.FEMALE, 11, 18));
//	tournamentRepository.save(new Tournament("Torneo Juniors 2020" , "Apertura 2020" , 4 , 18, 17));

	}
}
