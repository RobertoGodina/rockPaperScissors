package com.rps.rockPaperScissors.service.mapper;

import com.rps.rockPaperScissors.domain.StatsDB;
import com.rps.rockPaperScissors.domain.stats.GetStatsResponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StatsMapperService {

    private static final ModelMapper modelMapper = new ModelMapper();

    public GetStatsResponseVO getStats(StatsDB stats) {
        return modelMapper.map(stats, GetStatsResponseVO.class);
    }
}
