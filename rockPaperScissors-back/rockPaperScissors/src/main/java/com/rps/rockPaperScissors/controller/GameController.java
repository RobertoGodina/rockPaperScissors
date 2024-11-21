package com.rps.rockPaperScissors.controller;

import com.rps.rockPaperScissors.domain.game.Move;
import com.rps.rockPaperScissors.domain.game.PlayResponseVO;
import com.rps.rockPaperScissors.service.GameService;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {

    public GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(value = "/play", produces = {"application/json"})
    public ResponseEntity<PlayResponseVO> play(
            @RequestParam Move userMove,
            @RequestHeader(name = "Authorization") String authorization,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(gameService.play(userMove, authorization));
    }
}