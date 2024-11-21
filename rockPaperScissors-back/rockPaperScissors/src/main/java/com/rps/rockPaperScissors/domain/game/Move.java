package com.rps.rockPaperScissors.domain.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

@AllArgsConstructor
@Getter
public enum Move {
    PAPER,
    SCISSORS,
    ROCK;

    public static Move getRandomMove(){
        Move[] moves = Move.values();
        int randomIndex = new Random().nextInt(moves.length);

        return moves[randomIndex];
    }
}
