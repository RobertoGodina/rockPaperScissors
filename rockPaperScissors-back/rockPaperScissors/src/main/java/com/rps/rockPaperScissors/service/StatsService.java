package com.rps.rockPaperScissors.service;

import com.rps.rockPaperScissors.domain.stats.GetAllStatsResponseVO;
import com.rps.rockPaperScissors.domain.stats.GetStatsResponseVO;

import java.util.List;

public interface StatsService {

    GetStatsResponseVO getStats(String authorization);

    List<GetAllStatsResponseVO> getUsersStats();

}
