package com.rps.rockPaperScissors.repository;

import com.rps.rockPaperScissors.domain.GameHistoryDB;
import com.rps.rockPaperScissors.domain.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface GameHistoryRepository extends JpaRepository<GameHistoryDB, Long>, JpaSpecificationExecutor<GameHistoryDB> {

    Optional<List<GameHistoryDB>> findByUser(UserDB user);
}
