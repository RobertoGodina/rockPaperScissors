package com.rps.rockPaperScissors.service;

import com.rps.rockPaperScissors.domain.login.LoginRequestVO;
import com.rps.rockPaperScissors.domain.register.UserRequestVO;
import com.rps.rockPaperScissors.domain.token.ApiTokenVO;
import com.rps.rockPaperScissors.domain.token.RefreshTokenRequestVO;

public interface AuthService {

    ApiTokenVO login(LoginRequestVO loginRequest);

    ApiTokenVO refreshToken(RefreshTokenRequestVO refreshTokenRequest);

    void logout(String authorization);

}
