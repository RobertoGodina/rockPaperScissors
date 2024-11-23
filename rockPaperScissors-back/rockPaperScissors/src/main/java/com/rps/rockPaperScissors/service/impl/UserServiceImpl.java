package com.rps.rockPaperScissors.service.impl;

import com.rps.rockPaperScissors.domain.UserDB;
import com.rps.rockPaperScissors.domain.token.ApiTokenVO;
import com.rps.rockPaperScissors.domain.user.GetUserResponseVO;
import com.rps.rockPaperScissors.domain.user.UpdateUserRequestVO;
import com.rps.rockPaperScissors.exception.AppErrorCode;
import com.rps.rockPaperScissors.exception.CustomException;
import com.rps.rockPaperScissors.repository.UserRepository;
import com.rps.rockPaperScissors.service.JwtTokenService;
import com.rps.rockPaperScissors.service.UserService;
import com.rps.rockPaperScissors.service.mapper.UserDatabaseMapperService;
import lombok.extern.java.Log;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDatabaseMapperService userDatabaseMapperService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;


    public UserServiceImpl(UserRepository userRepository, UserDatabaseMapperService userDatabaseMapperService, BCryptPasswordEncoder passwordEncoder, JwtTokenService jwtTokenService) {

        this.userRepository = userRepository;
        this.userDatabaseMapperService = userDatabaseMapperService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }


    @Override
    public GetUserResponseVO getUser(String authorization) {
        UserDB user = userRepository.findByApiToken(authorization.substring(7))
                .orElseThrow(() -> new CustomException(AppErrorCode.BUSI_APITOKEN.getReasonPhrase()));

        return userDatabaseMapperService.buildReturnUser(user);
    }

    @Override
    public ApiTokenVO updateUser(UpdateUserRequestVO updateUserRequest, String authorization) {

        UserDB user = userRepository.findByApiToken(authorization.substring(7))
                .orElseThrow(() -> new CustomException(AppErrorCode.BUSI_APITOKEN.getReasonPhrase()));

        ApiTokenVO response = new ApiTokenVO(jwtTokenService.generateToken(updateUserRequest.getUsername()),
                jwtTokenService.generateRefreshToken(updateUserRequest.getUsername()));

        user.setEmail(updateUserRequest.getEmail());
        user.setUsername(updateUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        user.setApiToken(response.getApiToken());
        user.setRefreshToken(response.getRefreshApiToken());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.severe("Update user error" + e.getMessage());
            throw new CustomException(AppErrorCode.BUSI_SQL.getReasonPhrase());
        }

        return response;
    }
}