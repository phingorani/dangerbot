package com.waffle.dangerbot.repository;

import com.waffle.dangerbot.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    GameSession findGameSessionByChallengerId(long challengerId);

    GameSession findGameSessionByChallengedId(long challengedId);

    @Query("SELECT * FROM game_session WHERE (challenged_id=?2 OR challenged_id=?1) AND accpeted_ind=?3")
    GameSession findGameSessionByChallengedIdOrChallengerId(long challengedId, long challengerId, Boolean acceptedInd);
}