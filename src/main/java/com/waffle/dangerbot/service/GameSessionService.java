package com.waffle.dangerbot.service;

import com.waffle.dangerbot.entity.GameSession;
import com.waffle.dangerbot.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameSessionService {
    @Autowired
    private final GameSessionRepository gameSessionRepository;

    public GameSessionService(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    public void save(GameSession gameSession) {
        gameSessionRepository.save(gameSession);
    }

    public GameSession findByChallengerId(long challengerId) {
        return gameSessionRepository.findGameSessionByChallengerId(challengerId);
    }

    public void delete(GameSession gameSession){ gameSessionRepository.delete(gameSession);}

    public GameSession findByChallengedId(long challengedId) {
        return gameSessionRepository.findGameSessionByChallengedId(challengedId);
    }

    public GameSession findByChallengerIdOrChallengedIdAndAcceptedInd(long id) {
        return gameSessionRepository.findGameSessionByChallengedIdOrChallengerId(id, id, Boolean.TRUE);
    }
}
