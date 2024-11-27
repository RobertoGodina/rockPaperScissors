package com.rps.rockPaperScissors.service.mapper;

import com.rps.rockPaperScissors.domain.GameHistoryDB;
import com.rps.rockPaperScissors.domain.StatsDB;
import com.rps.rockPaperScissors.domain.game.GameHistoryResponseVO;
import com.rps.rockPaperScissors.domain.stats.GetAllStatsResponseVO;
import com.rps.rockPaperScissors.domain.stats.GetStatsResponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatsMapperService {

    private static final ModelMapper modelMapper = new ModelMapper();

    public GetStatsResponseVO getStats(StatsDB stats) {
        return modelMapper.map(stats, GetStatsResponseVO.class);
    }

    public List<GetAllStatsResponseVO> getUsersStats(List<StatsDB> allUsersStats) {
        List<GetAllStatsResponseVO> response = new ArrayList<>();

        for (StatsDB item : allUsersStats) {
            GetAllStatsResponseVO userState = modelMapper.map(item, GetAllStatsResponseVO.class);
            userState.setUsername(item.getUser().getUsername());
            response.add(userState);
        }

        return response;
    }
}
