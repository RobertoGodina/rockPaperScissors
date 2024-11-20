package com.rps.rockPaperScissors.repository;

import com.rps.rockPaperScissors.domain.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDB, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
