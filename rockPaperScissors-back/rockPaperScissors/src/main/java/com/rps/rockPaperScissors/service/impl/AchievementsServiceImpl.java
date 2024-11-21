package com.rps.rockPaperScissors.service.impl;

import com.rps.rockPaperScissors.domain.AchievementsDB;
import com.rps.rockPaperScissors.domain.UserDB;
import com.rps.rockPaperScissors.domain.achievements.GetAchievementsResponseVO;
import com.rps.rockPaperScissors.exception.AppErrorCode;
import com.rps.rockPaperScissors.exception.CustomException;
import com.rps.rockPaperScissors.repository.AchievementsRepository;
import com.rps.rockPaperScissors.repository.UserRepository;
import com.rps.rockPaperScissors.service.AchievementsService;
import com.rps.rockPaperScissors.service.mapper.AchievementsMapperService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log
@Service
@Transactional
public class AchievementsServiceImpl implements AchievementsService {

    private final UserRepository userRepository;
    private final AchievementsRepository achievementsRepository;
    private final AchievementsMapperService achievementsMapperService;

    public AchievementsServiceImpl(UserRepository userRepository,
                                   AchievementsRepository achievementsRepository,
                                   AchievementsMapperService achievementsMapperService) {
        this.userRepository = userRepository;
        this.achievementsRepository = achievementsRepository;
        this.achievementsMapperService = achievementsMapperService;
    }

    @Override
    public GetAchievementsResponseVO getAchievements(String authorization) {
        UserDB user = userRepository.findByApiToken(authorization.substring(7))
                .orElseThrow(() -> new CustomException(AppErrorCode.BUSI_APITOKEN.getReasonPhrase()));

        AchievementsDB achievements = achievementsRepository.findByUser(user);

        GetAchievementsResponseVO response = new GetAchievementsResponseVO();

        if (achievements != null) {
            response = achievementsMapperService.getAchievements(achievements);
        }

        return response;
    }
}
