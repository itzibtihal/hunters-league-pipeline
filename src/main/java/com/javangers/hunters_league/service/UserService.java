package com.javangers.hunters_league.service;

import com.javangers.hunters_league.domain.User;

public interface UserService {
    public User login(User user);
    public User register(User user);
    public User findUserByEmail(String email);
}
