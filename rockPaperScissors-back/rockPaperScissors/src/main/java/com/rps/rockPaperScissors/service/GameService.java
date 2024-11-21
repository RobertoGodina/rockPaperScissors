package com.rps.rockPaperScissors.service;

import com.rps.rockPaperScissors.domain.game.GameHistoryResponseVO;
import com.rps.rockPaperScissors.domain.game.Move;
import com.rps.rockPaperScissors.domain.game.PlayResponseVO;

import java.util.List;

public interface GameService {

    PlayResponseVO play(Move userMove, String authorization);

    List<GameHistoryResponseVO> gameHistory(String authorization);
}
