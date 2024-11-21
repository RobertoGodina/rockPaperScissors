package com.rps.rockPaperScissors.controller;

import com.rps.rockPaperScissors.domain.achievements.GetAchievementsResponseVO;
import com.rps.rockPaperScissors.service.AchievementsService;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/achievements")
public class AchievementsController {

    public AchievementsService achievementsService;

    public AchievementsController(AchievementsService achievementsService) {
        this.achievementsService = achievementsService;
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<GetAchievementsResponseVO> gameHistory(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(achievementsService.getAchievements(authorization));
    }
}
