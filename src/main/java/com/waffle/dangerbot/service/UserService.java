package com.waffle.dangerbot.service;

import com.waffle.dangerbot.entity.User;
import com.waffle.dangerbot.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByDiscordId(long id) {
       return userRepository.findByDiscordId(id);
    }
}
