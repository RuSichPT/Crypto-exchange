package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;

import java.util.Optional;

public interface UserService {


    void saveUser(User user);

    User createUser(String userName, String email, Wallet wallet);

    Optional<User> findUserByNameOrEmail(String username, String email);

    Optional<User> findUserBySecretKey(String secretKey);
}
