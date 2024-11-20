package com.rps.rockPaperScissors.service.impl;

import com.rps.rockPaperScissors.service.JwtTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.refreshSecretKey}")
    private String REFRESH_SECRET_KEY;


    @Override
    public String generateToken(String username) {
        int expiration = 1000 * 60 * 60; // 1 hour
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 1 hour
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    @Override
    public String validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateRefreshToken(String username) {
        int expiration = 1000 * 60 * 60 * 24 * 7; // 1 hour
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 7 days
                .signWith(Keys.hmacShaKeyFor(REFRESH_SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(REFRESH_SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
