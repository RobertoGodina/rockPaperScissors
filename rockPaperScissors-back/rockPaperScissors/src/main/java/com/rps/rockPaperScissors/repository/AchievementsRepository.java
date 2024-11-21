package com.rps.rockPaperScissors.repository;

import com.rps.rockPaperScissors.domain.AchievementsDB;
import com.rps.rockPaperScissors.domain.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AchievementsRepository extends JpaRepository<AchievementsDB, Long>, JpaSpecificationExecutor<AchievementsDB> {

    AchievementsDB findByUser(UserDB user);
}
