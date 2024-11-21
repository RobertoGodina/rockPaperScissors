package com.rps.rockPaperScissors.repository;

import com.rps.rockPaperScissors.domain.GameHistory;
import com.rps.rockPaperScissors.domain.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long>, JpaSpecificationExecutor<GameHistory> {
    
}
