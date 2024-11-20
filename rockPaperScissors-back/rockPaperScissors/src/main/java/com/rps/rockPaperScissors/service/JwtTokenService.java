package com.rps.rockPaperScissors.service;

public interface JwtTokenService {

    String generateToken(String username);

    String validateToken(String token);

    String generateRefreshToken(String username);

    String validateRefreshToken(String token);

}
