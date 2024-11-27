package com.rps.rockPaperScissors.controller;

import com.rps.rockPaperScissors.domain.stats.GetAllStatsResponseVO;
import com.rps.rockPaperScissors.domain.stats.GetStatsResponseVO;
import com.rps.rockPaperScissors.service.StatsService;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stats")
@CrossOrigin(origins = "http://localhost:4200")
public class StatsController {

    public StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<GetStatsResponseVO> getStats(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(statsService.getStats(authorization));
    }

    @GetMapping(value = "/allStats", produces = {"application/json"})
    public ResponseEntity<List<GetAllStatsResponseVO>> getUsersStats(
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(statsService.getUsersStats());
    }
}
