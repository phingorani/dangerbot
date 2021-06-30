package com.waffle.dangerbot.repository;

import com.waffle.dangerbot.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    GameSession findGameSessionByChallengerId(long challengerId);

    GameSession findGameSessionByChallengedId(long challengedId);

    @Query(name="SELECT g.game_session_id, g.bet_amount, g.challenger_id, g.challenged_id, g.accepted_ind FROM game_session g WHERE (g.challenged_id=:challengedId OR g.challenged_id=:challengerId) AND g.accpted_ind=TRUE", nativeQuery = true)
    GameSession findGameSessionByChallengedIdOrChallengerId(long challengedId, long challengerId);
}