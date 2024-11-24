package com.rps.rockPaperScissors.repository;

import com.rps.rockPaperScissors.domain.StatsDB;
import com.rps.rockPaperScissors.domain.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StatsRepository extends JpaRepository<StatsDB, Long>, JpaSpecificationExecutor<StatsDB> {

    StatsDB findByUser(UserDB user);
}
