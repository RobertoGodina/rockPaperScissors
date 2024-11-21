package com.rps.rockPaperScissors.service.mapper;

import com.rps.rockPaperScissors.domain.GameHistory;
import com.rps.rockPaperScissors.domain.game.GameHistoryResponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameHistoryMapperService {

    private static final ModelMapper modelMapper = new ModelMapper();

    public List<GameHistoryResponseVO> getGameHistories(List<GameHistory> gameHistories){
        List<GameHistoryResponseVO> response = new ArrayList<>();

        for(GameHistory item: gameHistories){
            response.add(modelMapper.map(item, GameHistoryResponseVO.class));
        }
        return response;
    }
}
