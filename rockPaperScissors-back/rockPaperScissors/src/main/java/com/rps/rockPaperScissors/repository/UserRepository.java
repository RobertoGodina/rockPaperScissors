package com.rps.rockPaperScissors.repository;

import com.rps.rockPaperScissors.domain.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDB, Long>, JpaSpecificationExecutor<UserDB> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<UserDB> findByUsername(String username);

    Optional<UserDB> findByApiToken(String token);
}
