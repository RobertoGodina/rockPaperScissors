package com.rps.rockPaperScissors.controller;

import com.rps.rockPaperScissors.domain.login.LoginRequestVO;
import com.rps.rockPaperScissors.domain.token.ApiTokenVO;
import com.rps.rockPaperScissors.domain.token.RefreshTokenRequestVO;
import com.rps.rockPaperScissors.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", produces = {"application/json"})
    public ResponseEntity<ApiTokenVO> login(
            @Valid @RequestBody LoginRequestVO loginRequest,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequest));
    }

    @PostMapping(value = "/refresh", produces = {"application/json"})
    public ResponseEntity<ApiTokenVO> refreshToken(
            @Valid @RequestBody RefreshTokenRequestVO refreshTokenRequest,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        authService.logout(authorization);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
