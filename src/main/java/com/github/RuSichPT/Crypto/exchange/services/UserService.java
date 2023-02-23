package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;

import java.util.Optional;

public interface UserService {

    void saveUser(User user);

    Optional<User> findUserByName(String username);
}
