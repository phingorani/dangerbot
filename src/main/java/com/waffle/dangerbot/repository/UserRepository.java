package com.waffle.dangerbot.repository;

import com.waffle.dangerbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
