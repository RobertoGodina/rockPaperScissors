package com.rps.rockPaperScissors.service.impl;

import com.rps.rockPaperScissors.domain.UserDB;
import com.rps.rockPaperScissors.domain.register.UserRequestVO;
import com.rps.rockPaperScissors.exception.AppErrorCode;
import com.rps.rockPaperScissors.exception.CustomException;
import com.rps.rockPaperScissors.repository.UserRepository;
import com.rps.rockPaperScissors.service.RockPaperScissorsService;
import com.rps.rockPaperScissors.service.mapper.UserDatabaseMapperService;
import lombok.extern.java.Log;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log
@Service
@Transactional
public class RockPaperScissorsServiceImpl implements RockPaperScissorsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private UserDatabaseMapperService userDatabaseMapperService;

    public RockPaperScissorsServiceImpl(UserRepository userRepository,
                                        BCryptPasswordEncoder passwordEncoder,
                                        UserDatabaseMapperService userDatabaseMapperService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDatabaseMapperService = userDatabaseMapperService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRequestVO user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new CustomException(AppErrorCode.BUSI_USERNAME.getReasonPhrase());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomException(AppErrorCode.BUSI_EMAIL.getReasonPhrase());

        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserDB userDBEntity = userDatabaseMapperService.buildUserEntity(user);

        try{
            userRepository.save(userDBEntity);
        }catch (Exception e){
            log.severe("Database register error" + e.getMessage());

            throw new RuntimeException(AppErrorCode.BUSI_SQL.getReasonPhrase(), e);
        }
    }
}
