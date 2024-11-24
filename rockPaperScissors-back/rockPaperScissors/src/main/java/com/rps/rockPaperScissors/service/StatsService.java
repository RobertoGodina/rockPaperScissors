package com.rps.rockPaperScissors.service;

import com.rps.rockPaperScissors.domain.stats.GetStatsResponseVO;

public interface StatsService {

    GetStatsResponseVO getStats(String authorization);

}
