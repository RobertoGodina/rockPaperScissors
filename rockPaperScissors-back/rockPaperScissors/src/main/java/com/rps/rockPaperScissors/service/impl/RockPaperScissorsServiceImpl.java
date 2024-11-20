package com.rps.rockPaperScissors.service.impl;

import com.rps.rockPaperScissors.domain.UserDB;
import com.rps.rockPaperScissors.domain.login.LoginRequestVO;
import com.rps.rockPaperScissors.domain.register.UserRequestVO;
import com.rps.rockPaperScissors.domain.token.ApiTokenVO;
import com.rps.rockPaperScissors.domain.token.RefreshTokenRequestVO;
import com.rps.rockPaperScissors.exception.AppErrorCode;
import com.rps.rockPaperScissors.exception.CustomException;
import com.rps.rockPaperScissors.repository.UserRepository;
import com.rps.rockPaperScissors.service.JwtTokenService;
import com.rps.rockPaperScissors.service.RockPaperScissorsService;
import com.rps.rockPaperScissors.service.mapper.UserDatabaseMapperService;
import lombok.extern.java.Log;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Log
@Service
@Transactional
public class RockPaperScissorsServiceImpl implements RockPaperScissorsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDatabaseMapperService userDatabaseMapperService;
    private final JwtTokenService jwtTokenService;

    public RockPaperScissorsServiceImpl(UserRepository userRepository,
                                        BCryptPasswordEncoder passwordEncoder,
                                        UserDatabaseMapperService userDatabaseMapperService,
                                        JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDatabaseMapperService = userDatabaseMapperService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void register(UserRequestVO user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new CustomException(AppErrorCode.BUSI_USERNAME.getReasonPhrase());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomException(AppErrorCode.BUSI_EMAIL.getReasonPhrase());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserDB userDBEntity = userDatabaseMapperService.buildUserEntity(user);

        try {
            userRepository.save(userDBEntity);
        } catch (Exception e) {
            log.severe("Database register error" + e.getMessage());
            throw new RuntimeException(AppErrorCode.BUSI_SQL.getReasonPhrase(), e);
        }
    }

    @Override
    public ApiTokenVO login(LoginRequestVO loginRequest) {

        UserDB user = validateUser(loginRequest.getUsername());

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        ApiTokenVO response = new ApiTokenVO(jwtTokenService.generateToken(loginRequest.getUsername()),
                jwtTokenService.generateRefreshToken(loginRequest.getUsername()));

        user.setApiToken(response.getApiToken());
        user.setRefreshToken(response.getRefreshApiToken());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.severe("Database login error" + e.getMessage());
            throw new RuntimeException(AppErrorCode.BUSI_SQL.getReasonPhrase(), e);
        }
        return response;
    }

    @Override
    public ApiTokenVO refreshToken(RefreshTokenRequestVO refreshTokenRequest) {

        UserDB user = validateUser(refreshTokenRequest.getUsername());

        if (!refreshTokenRequest.getRefreshApiToken().equals(user.getRefreshToken())) {
            throw new CustomException(AppErrorCode.BUSI_REFRESH_TOKEN.getReasonPhrase());
        }

        ApiTokenVO response = new ApiTokenVO(jwtTokenService.generateToken(user.getUsername()),
                jwtTokenService.generateRefreshToken(user.getUsername()));

        user.setApiToken(response.getApiToken());
        user.setRefreshToken(response.getRefreshApiToken());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.severe("Database login error" + e.getMessage());
            throw new RuntimeException(AppErrorCode.BUSI_SQL.getReasonPhrase(), e);
        }
        return response;
    }

    private UserDB validateUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(AppErrorCode.BUSI_USER.getReasonPhrase()));
    }
}
