package com.rps.rockPaperScissors.service.impl;

import com.rps.rockPaperScissors.domain.GameHistoryDB;
import com.rps.rockPaperScissors.domain.StatsDB;
import com.rps.rockPaperScissors.domain.UserDB;
import com.rps.rockPaperScissors.domain.game.GameHistoryResponseVO;
import com.rps.rockPaperScissors.domain.game.GameResult;
import com.rps.rockPaperScissors.domain.game.Move;
import com.rps.rockPaperScissors.domain.game.PlayResponseVO;
import com.rps.rockPaperScissors.exception.AppErrorCode;
import com.rps.rockPaperScissors.exception.BusinessException;
import com.rps.rockPaperScissors.exception.DatabaseOperationException;
import com.rps.rockPaperScissors.repository.GameHistoryRepository;
import com.rps.rockPaperScissors.repository.StatsRepository;
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

        PlayResponseVO response = new PlayResponseVO(userMove, computerMove, gameResult, 0 ,0);

        if (authorization != null) {

            UserDB user = userRepository.findByApiToken(authorization.substring(7))
                    .orElseThrow(() -> new BusinessException(AppErrorCode.BUSI_APITOKEN.getReasonPhrase()));

            response = updateStats(user, userMove, gameResult, response);
            saveGameHistory(user, userMove, computerMove, gameResult, response.getScore());

        }

        return response;
    }

    @Override
    public List<GameHistoryResponseVO> gameHistory(String authorization) {

        UserDB user = userRepository.findByApiToken(authorization.substring(7))
                .orElseThrow(() -> new BusinessException(AppErrorCode.BUSI_APITOKEN.getReasonPhrase()));

        List<GameHistoryDB> gameHistories = gameHistoryRepository.findByUser(user)
                .orElseThrow(() -> new BusinessException(AppErrorCode.BUSI_USER.getReasonPhrase()));

        return gameHistoryMapperService.getGameHistories(gameHistories);

    }

    public PlayResponseVO updateStats(UserDB user, Move userMove, GameResult gameResult, PlayResponseVO playResponse) {
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

        int score = stats.getGamePoints();
        int consecutiveWon = stats.getConsecutiveWon();
        switch (gameResult) {
            case LOSE -> {
                stats.setGamesLost(stats.getGamesLost() + 1);
                stats.setConsecutiveWon(0);
            }
            case WIN -> {
                consecutiveWon = stats.getConsecutiveWon() + 1;

                int gamePoints = stats.getGamePoints() + (100 * (Math.max(consecutiveWon, 1)));
                score = gamePoints;

                stats.setGamesWon(stats.getGamesWon() + 1) ;
                stats.setConsecutiveWon(consecutiveWon);
                stats.setGamePoints(gamePoints);
            }
            case TIE -> {
                stats.setGamesTied(stats.getGamesTied() + 1);
            }
        }

        stats.setGamesPlayed(stats.getGamesPlayed() + 1);

        try {
            statsRepository.save(stats);

        } catch (Exception e) {
            throw new DatabaseOperationException(AppErrorCode.BUSI_SQL.getReasonPhrase());
        }

        playResponse.setConsecutiveWon(consecutiveWon);
        playResponse.setScore(score);

        return playResponse;
    }

    public void saveGameHistory(UserDB user, Move userMove, Move computerMove, GameResult gameResult, int score) {
        GameHistoryDB gameHistoryDB = new GameHistoryDB();
        gameHistoryDB.setUser(user);
        gameHistoryDB.setUserMove(userMove);
        gameHistoryDB.setComputerMove(computerMove);
        gameHistoryDB.setResult(gameResult);
        gameHistoryDB.setScore(score);

        try {
            gameHistoryRepository.save(gameHistoryDB);

        } catch (Exception e) {
            throw new DatabaseOperationException(AppErrorCode.BUSI_SQL.getReasonPhrase());
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
            default -> throw new BusinessException("Invalid move: " + userMove);
        };
    }
}
