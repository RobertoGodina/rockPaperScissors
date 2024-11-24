package com.rps.rockPaperScissors.service.impl;

import com.rps.rockPaperScissors.domain.StatsDB;
import com.rps.rockPaperScissors.domain.GameHistoryDB;
import com.rps.rockPaperScissors.domain.UserDB;
import com.rps.rockPaperScissors.domain.game.GameHistoryResponseVO;
import com.rps.rockPaperScissors.domain.game.GameResult;
import com.rps.rockPaperScissors.domain.game.Move;
import com.rps.rockPaperScissors.domain.game.PlayResponseVO;
import com.rps.rockPaperScissors.exception.AppErrorCode;
import com.rps.rockPaperScissors.exception.CustomException;
import com.rps.rockPaperScissors.repository.StatsRepository;
import com.rps.rockPaperScissors.repository.GameHistoryRepository;
import com.rps.rockPaperScissors.repository.UserRepository;
import com.rps.rockPaperScissors.service.GameService;
import com.rps.rockPaperScissors.service.mapper.GameHistoryMapperService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameHistoryRepository gameHistoryRepository;
    private final UserRepository userRepository;
    private final GameHistoryMapperService gameHistoryMapperService;
    private final StatsRepository statsRepository;

    public GameServiceImpl(GameHistoryRepository gameHistoryRepository,
                           UserRepository userRepository,
                           GameHistoryMapperService gameHistoryMapperService,
                           StatsRepository statsRepository) {
        this.gameHistoryRepository = gameHistoryRepository;
        this.gameHistoryMapperService = gameHistoryMapperService;
        this.userRepository = userRepository;
        this.statsRepository = statsRepository;
    }


    @Override
    public PlayResponseVO play(Move userMove, String authorization) {

        Move computerMove = Move.getRandomMove();
        GameResult gameResult = decideWinner(userMove, computerMove);

        if (authorization != null) {

            UserDB user = userRepository.findByApiToken(authorization.substring(7))
                    .orElseThrow(() -> new CustomException(AppErrorCode.BUSI_APITOKEN.getReasonPhrase()));

            saveGameHistory(user, userMove, computerMove, gameResult);
            updateStats(user, userMove, gameResult);
        }

        return new PlayResponseVO(userMove, computerMove, gameResult);
    }

    @Override
    public List<GameHistoryResponseVO> gameHistory(String authorization) {

        UserDB user = userRepository.findByApiToken(authorization.substring(7))
                .orElseThrow(() -> new CustomException(AppErrorCode.BUSI_APITOKEN.getReasonPhrase()));

        List<GameHistoryDB> gameHistories = gameHistoryRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(AppErrorCode.BUSI_USER.getReasonPhrase()));

        return gameHistoryMapperService.getGameHistories(gameHistories);

    }

    public void updateStats(UserDB user, Move userMove, GameResult gameResult) {
        StatsDB stats = statsRepository.findByUser(user);

        if (stats == null) {
            stats = new StatsDB();
            stats.setUser(user);
        }

        switch (userMove) {
            case SCISSORS -> stats.setScissorsPlayed(stats.getScissorsPlayed() + 1);
            case ROCK -> stats.setRockPlayed(stats.getRockPlayed() + 1);
            case PAPER -> stats.setPaperPlayed(stats.getPaperPlayed() + 1);
        }

        switch (gameResult) {
            case LOSE -> stats.setGamesLost(stats.getGamesLost() + 1);
            case WIN -> stats.setGamesWon(stats.getGamesWon() + 1);
            case TIE -> stats.setGamesTied(stats.getGamesTied() + 1);
        }

        stats.setGamesPlayed(stats.getGamesPlayed() + 1);

        try {
            statsRepository.save(stats);

        } catch (Exception e) {
            throw new CustomException(AppErrorCode.BUSI_SQL.getReasonPhrase());
        }
    }

    public void saveGameHistory(UserDB user, Move userMove, Move computerMove, GameResult gameResult) {
        GameHistoryDB gameHistoryDB = new GameHistoryDB();
        gameHistoryDB.setUser(user);
        gameHistoryDB.setUserMove(userMove);
        gameHistoryDB.setComputerMove(computerMove);
        gameHistoryDB.setResult(gameResult);

        try {
            gameHistoryRepository.save(gameHistoryDB);

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
