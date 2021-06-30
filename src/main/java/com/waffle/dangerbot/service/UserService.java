package com.waffle.dangerbot.service;

import com.waffle.dangerbot.entity.DiscordUser;
import com.waffle.dangerbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(DiscordUser discordUser) {
        userRepository.save(discordUser);
    }

    public DiscordUser findByDiscordId(long id) {
       return userRepository.findByDiscordId(id);
    }
}
