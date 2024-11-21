package com.rps.rockPaperScissors.service.impl;

import com.rps.rockPaperScissors.domain.AchievementsDB;
import com.rps.rockPaperScissors.domain.GameHistoryDB;
import com.rps.rockPaperScissors.domain.UserDB;
import com.rps.rockPaperScissors.domain.game.GameHistoryResponseVO;
import com.rps.rockPaperScissors.domain.game.GameResult;
import com.rps.rockPaperScissors.domain.game.Move;
import com.rps.rockPaperScissors.domain.game.PlayResponseVO;
import com.rps.rockPaperScissors.exception.AppErrorCode;
import com.rps.rockPaperScissors.exception.CustomException;
import com.rps.rockPaperScissors.repository.AchievementsRepository;
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
    private final AchievementsRepository achievementsRepository;

    public GameServiceImpl(GameHistoryRepository gameHistoryRepository,
                           UserRepository userRepository,
                           GameHistoryMapperService gameHistoryMapperService,
                           AchievementsRepository achievementsRepository) {
        this.gameHistoryRepository = gameHistoryRepository;
        this.gameHistoryMapperService = gameHistoryMapperService;
        this.userRepository = userRepository;
        this.achievementsRepository = achievementsRepository;
    }


    @Override
    public PlayResponseVO play(Move userMove, String authorization) {

        UserDB user = userRepository.findByApiToken(authorization.substring(7))
                .orElseThrow(() -> new CustomException(AppErrorCode.BUSI_APITOKEN.getReasonPhrase()));

        Move computerMove = Move.getRandomMove();
        GameResult gameResult = decideWinner(userMove, computerMove);

        saveGameHistory(user, userMove, computerMove, gameResult);
        updateAchievements(user, userMove, gameResult);

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

    public void updateAchievements(UserDB user, Move userMove, GameResult gameResult) {
        AchievementsDB achievements = achievementsRepository.findByUser(user);

        if (achievements == null) {
            achievements = new AchievementsDB();
            achievements.setUser(user);
        }

        switch (userMove) {
            case SCISSORS -> achievements.setScissorsPlayed(achievements.getScissorsPlayed() + 1);
            case ROCK -> achievements.setRockPlayed(achievements.getRockPlayed() + 1);
            case PAPER -> achievements.setPaperPlayed(achievements.getPaperPlayed() + 1);
        }

        switch (gameResult) {
            case LOSE -> achievements.setGamesLost(achievements.getGamesLost() + 1);
            case WIN -> achievements.setGamesWon(achievements.getGamesWon() + 1);
            case TIE -> achievements.setGamesTied(achievements.getGamesTied() + 1);
        }

        achievements.setGamesPlayed(achievements.getGamesPlayed() + 1);

        try {
            achievementsRepository.save(achievements);

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
