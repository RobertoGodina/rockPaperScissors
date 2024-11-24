package com.rps.rockPaperScissors.controller;

import com.rps.rockPaperScissors.domain.game.GameHistoryResponseVO;
import com.rps.rockPaperScissors.domain.game.Move;
import com.rps.rockPaperScissors.domain.game.PlayResponseVO;
import com.rps.rockPaperScissors.service.GameService;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {

    public GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "/play", produces = {"application/json"})
    public ResponseEntity<PlayResponseVO> play(
            @RequestParam Move userMove,
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(gameService.play(userMove, authorization));
    }

    @GetMapping(value = "/gameHistory", produces = {"application/json"})
    public ResponseEntity<List<GameHistoryResponseVO>> gameHistory(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(gameService.gameHistory(authorization));
    }
}
