package com.score_keeper.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.score_keeper.models.ERole;
import com.score_keeper.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
