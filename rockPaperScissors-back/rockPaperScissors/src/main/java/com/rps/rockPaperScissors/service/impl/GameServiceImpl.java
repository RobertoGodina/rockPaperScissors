package com.rps.rockPaperScissors.service.impl;

import com.rps.rockPaperScissors.domain.GameHistory;
import com.rps.rockPaperScissors.domain.UserDB;
import com.rps.rockPaperScissors.domain.game.GameResult;
import com.rps.rockPaperScissors.domain.game.Move;
import com.rps.rockPaperScissors.domain.game.PlayResponseVO;
import com.rps.rockPaperScissors.domain.login.LoginRequestVO;
import com.rps.rockPaperScissors.domain.register.UserRequestVO;
import com.rps.rockPaperScissors.domain.token.ApiTokenVO;
import com.rps.rockPaperScissors.domain.token.RefreshTokenRequestVO;
import com.rps.rockPaperScissors.exception.AppErrorCode;
import com.rps.rockPaperScissors.exception.CustomException;
import com.rps.rockPaperScissors.repository.GameHistoryRepository;
import com.rps.rockPaperScissors.repository.UserRepository;
import com.rps.rockPaperScissors.service.AuthService;
import com.rps.rockPaperScissors.service.GameService;
import com.rps.rockPaperScissors.service.JwtTokenService;
import com.rps.rockPaperScissors.service.mapper.UserDatabaseMapperService;
import lombok.extern.java.Log;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameHistoryRepository gameHistoryRepository;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;

    public GameServiceImpl(GameHistoryRepository gameHistoryRepository,
                           UserRepository userRepository,
                           JwtTokenService jwtTokenService) {
        this.gameHistoryRepository = gameHistoryRepository;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }


    @Override
    public PlayResponseVO play(Move userMove, String authorization) {

        String username = jwtTokenService.validateToken(authorization.substring(7));

        UserDB user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(AppErrorCode.BUSI_USERNAME_DB.getReasonPhrase()));

        Move computerMove = Move.getRandomMove();
        GameResult gameResult = decideWinner(userMove, computerMove);

        saveGameHistory(user, userMove, computerMove, gameResult);

        return new PlayResponseVO(userMove, computerMove, gameResult);
    }

    public void saveGameHistory(UserDB user, Move userMove, Move computerMove, GameResult gameResult) {
        GameHistory gameHistory = new GameHistory();
        gameHistory.setUser(user);
        gameHistory.setUserMove(userMove);
        gameHistory.setComputerMove(computerMove);
        gameHistory.setResult(gameResult);

        try {
            gameHistoryRepository.save(gameHistory);

        } catch (Exception e) {
            throw new CustomException(AppErrorCode.BUSI_SQL.getReasonPhrase());
        }
    }

    public GameResult decideWinner(Move userMove, Move computerMove) {
        if (userMove == computerMove) {
            return GameResult.TIE;
        }

        return switch (userMove) {
            case ROCK -> computerMove == Move.SCISSORS ? GameResult.WIN : GameResult.LOSE;
            case PAPER -> computerMove == Move.ROCK ? GameResult.WIN : GameResult.LOSE;
            case SCISSORS -> computerMove == Move.PAPER ? GameResult.WIN : GameResult.LOSE;
            default -> throw new IllegalArgumentException("Invalid move: " + userMove);
        };
    }
}
