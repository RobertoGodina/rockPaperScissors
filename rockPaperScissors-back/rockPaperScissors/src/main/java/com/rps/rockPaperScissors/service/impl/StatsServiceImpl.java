package com.rps.rockPaperScissors.service.impl;

import com.rps.rockPaperScissors.domain.StatsDB;
import com.rps.rockPaperScissors.domain.UserDB;
import com.rps.rockPaperScissors.domain.stats.GetStatsResponseVO;
import com.rps.rockPaperScissors.exception.AppErrorCode;
import com.rps.rockPaperScissors.exception.CustomException;
import com.rps.rockPaperScissors.repository.StatsRepository;
import com.rps.rockPaperScissors.repository.UserRepository;
import com.rps.rockPaperScissors.service.StatsService;
import com.rps.rockPaperScissors.service.mapper.StatsMapperService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log
@Service
@Transactional
public class StatsServiceImpl implements StatsService {

    private final UserRepository userRepository;
    private final StatsRepository statsRepository;
    private final StatsMapperService statsMapperService;

    public StatsServiceImpl(UserRepository userRepository,
                            StatsRepository statsRepository,
                            StatsMapperService statsMapperService) {
        this.userRepository = userRepository;
        this.statsRepository = statsRepository;
        this.statsMapperService = statsMapperService;
    }

    @Override
    public GetStatsResponseVO getStats(String authorization) {
        UserDB user = userRepository.findByApiToken(authorization.substring(7))
                .orElseThrow(() -> new CustomException(AppErrorCode.BUSI_APITOKEN.getReasonPhrase()));

        StatsDB stats = statsRepository.findByUser(user);

        GetStatsResponseVO response = new GetStatsResponseVO();

        if (stats != null) {
            response = statsMapperService.getStats(stats);
        }

        return response;
    }
}
