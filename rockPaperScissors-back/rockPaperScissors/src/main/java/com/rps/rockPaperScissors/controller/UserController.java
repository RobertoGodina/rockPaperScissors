package com.rps.rockPaperScissors.controller;

import com.rps.rockPaperScissors.domain.register.UserRequestVO;
import com.rps.rockPaperScissors.domain.token.ApiTokenVO;
import com.rps.rockPaperScissors.domain.user.GetUserResponseVO;
import com.rps.rockPaperScissors.domain.user.UpdateUserRequestVO;
import com.rps.rockPaperScissors.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", produces = {"application/json"})
    public ResponseEntity<GetUserResponseVO> getUser(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(authorization));
    }

    @PostMapping(value = "/updateUser", produces = {"application/json"})
    public ResponseEntity<ApiTokenVO> updateUser(
            @Valid @RequestBody UpdateUserRequestVO updateUserRequest,
            @RequestHeader(name = "Authorization") String authorization,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(updateUserRequest, authorization));
    }

    @PostMapping(value = "/register", produces = {"application/json"})
    public ResponseEntity<String> register(
            @Valid @RequestBody UserRequestVO userRequest,
            @RequestHeader(required = false, name = "X-Correlation-Id") String correlationId) {
        MDC.put("correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString());

        userService.register(userRequest);
        return ResponseEntity.created(URI.create("")).build();
    }
}
