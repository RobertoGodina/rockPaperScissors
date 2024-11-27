package com.rps.rockPaperScissors.domain.game;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Generated
public class PlayResponseVO {

    private Move userMove;

    private Move computerMove;

    private GameResult result;

    private int score;

    private int consecutiveWon;
}
