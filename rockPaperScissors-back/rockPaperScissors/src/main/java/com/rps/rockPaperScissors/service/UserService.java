package com.rps.rockPaperScissors.service;

import com.rps.rockPaperScissors.domain.register.UserRequestVO;
import com.rps.rockPaperScissors.domain.token.ApiTokenVO;
import com.rps.rockPaperScissors.domain.user.UpdateUserRequestVO;
import com.rps.rockPaperScissors.domain.user.GetUserResponseVO;

public interface UserService {

    GetUserResponseVO getUser(String authorization);

    ApiTokenVO updateUser(UpdateUserRequestVO updateUserRequest, String authorization);

    void register(UserRequestVO user);

}
