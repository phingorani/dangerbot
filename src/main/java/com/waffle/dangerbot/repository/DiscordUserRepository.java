package com.waffle.dangerbot.repository;

import com.waffle.dangerbot.entity.DiscordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscordUserRepository extends JpaRepository<DiscordUser, Long> {
    DiscordUser findByDiscordId(long discordId);
}
