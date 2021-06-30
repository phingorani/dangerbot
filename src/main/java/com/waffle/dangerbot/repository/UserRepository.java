package com.waffle.dangerbot.repository;

import com.waffle.dangerbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByDiscordId(long discordId);
}
