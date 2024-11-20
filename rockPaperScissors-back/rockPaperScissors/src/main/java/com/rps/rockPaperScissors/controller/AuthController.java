package com.rps.rockPaperScissors.controller;

import com.rps.rockPaperScissors.domain.login.LoginRequestVO;
import com.rps.rockPaperScissors.domain.register.UserRequestVO;
import com.rps.rockPaperScissors.domain.token.ApiTokenVO;
import com.rps.rockPaperScissors.domain.token.RefreshTokenRequestVO;
import com.rps.rockPaperScissors.service.RockPaperScissorsService;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public RockPaperScissorsService rockPaperScissorsService;

    public AuthController(RockPaperScissorsService rockPaperScissorsService) {
        this.rockPaperScissorsService = rockPaperScissorsService;
    }

    @PostMapping(value = "/login", produces = {"application/json"})
    public ResponseEntity<ApiTokenVO> login(
            @Valid @RequestBody LoginRequestVO loginRequest,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(rockPaperScissorsService.login(loginRequest));
    }

    @PostMapping(value = "/register", produces = {"application/json"})
    public ResponseEntity<String> register(
            @Valid @RequestBody UserRequestVO userRequest,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        rockPaperScissorsService.register(userRequest);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping(value = "/refresh", produces = {"application/json"})
    public ResponseEntity<ApiTokenVO> refreshToken(
            @Valid @RequestBody RefreshTokenRequestVO refreshTokenRequest,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(rockPaperScissorsService.refreshToken(refreshTokenRequest));
    }
}
