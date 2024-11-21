package com.rps.rockPaperScissors.service;

import com.rps.rockPaperScissors.domain.game.Move;
import com.rps.rockPaperScissors.domain.game.PlayResponseVO;

public interface GameService {

    PlayResponseVO play(Move userMove, String authorization);

}
