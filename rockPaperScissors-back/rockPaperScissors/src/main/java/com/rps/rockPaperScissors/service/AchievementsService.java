package com.rps.rockPaperScissors.service;

import com.rps.rockPaperScissors.domain.achievements.GetAchievementsResponseVO;

public interface AchievementsService {

    GetAchievementsResponseVO getAchievements(String authorization);

}
