package com.rps.rockPaperScissors.domain.game;

import com.fasterxml.jackson.annotation.JsonInclude;
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
}
