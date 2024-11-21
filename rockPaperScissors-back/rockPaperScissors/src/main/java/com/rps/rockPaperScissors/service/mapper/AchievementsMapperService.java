package com.rps.rockPaperScissors.service.mapper;

import com.rps.rockPaperScissors.domain.AchievementsDB;
import com.rps.rockPaperScissors.domain.achievements.GetAchievementsResponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AchievementsMapperService {

    private static final ModelMapper modelMapper = new ModelMapper();

    public GetAchievementsResponseVO getAchievements(AchievementsDB achievements) {
        return modelMapper.map(achievements, GetAchievementsResponseVO.class);
    }
}
