package com.rps.rockPaperScissors.domain.game;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Generated
public class GameHistoryResponseVO {

    private Long id;

    private Move userMove;

    private Move computerMove;

    private GameResult result;

    private LocalDateTime playedAt;

}
