package com.rps.rockPaperScissors.service.mapper;

import com.rps.rockPaperScissors.domain.UserDB;
import com.rps.rockPaperScissors.domain.register.UserRequestVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserDatabaseMapperService {
    private static final ModelMapper modelMapper = new ModelMapper();

    public UserDB buildUserEntity(UserRequestVO userRequest){
        return modelMapper.map(userRequest, UserDB.class);
    }

}
