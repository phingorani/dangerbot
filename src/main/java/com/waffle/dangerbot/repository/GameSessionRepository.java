package com.waffle.dangerbot.repository;

import com.waffle.dangerbot.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    GameSession findGameSessionByChallengerId(long challengerId);

    GameSession findGameSessionByChallengedId(long challengedId);

    GameSession findGameSessionByChallengedIdOrChallengerId(long challengedId, long challengerId);
}