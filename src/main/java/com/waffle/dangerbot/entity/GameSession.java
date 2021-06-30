package com.waffle.dangerbot.entity;

import javax.persistence.*;

@Entity
@Table(name = "game_session")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_session_id")
    private Long gameSessionId;

    @Column(name = "bet_amount")
    private Integer betAmount;

    @Column(name = "challenger_id")
    private Long challengerId;

    @Column(name = "challenged_id")
    private Long challengedId;

    @Column(name = "accepted_ind")
    private Boolean acceptedInd;

    public Boolean getAcceptedInd() {
        return acceptedInd;
    }

    public void setAcceptedInd(Boolean acceptedInd) {
        this.acceptedInd = acceptedInd;
    }

    public Long getGameSessionId() {
        return gameSessionId;
    }

    public void setGameSessionId(Long gameSessionId) {
        this.gameSessionId = gameSessionId;
    }

    public Integer getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(Integer betAmount) {
        this.betAmount = betAmount;
    }

    public Long getChallengerId() {
        return challengerId;
    }

    public void setChallengerId(Long challengerId) {
        this.challengerId = challengerId;
    }

    public Long getChallengedId() {
        return challengedId;
    }

    public void setChallengedId(Long challengedId) {
        this.challengedId = challengedId;
    }
}
