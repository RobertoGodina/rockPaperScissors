package com.rps.rockPaperScissors.service.mapper;

import com.rps.rockPaperScissors.domain.GameHistoryDB;
import com.rps.rockPaperScissors.domain.game.GameHistoryResponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameHistoryMapperService {

    private static final ModelMapper modelMapper = new ModelMapper();

    public List<GameHistoryResponseVO> getGameHistories(List<GameHistoryDB> gameHistories) {
        List<GameHistoryResponseVO> response = new ArrayList<>();

        for (GameHistoryDB item : gameHistories) {
            response.add(modelMapper.map(item, GameHistoryResponseVO.class));
        }
        return response;
    }
}
