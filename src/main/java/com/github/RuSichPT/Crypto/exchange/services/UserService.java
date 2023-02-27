package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;

import java.util.Optional;

public interface UserService {

    User createUser(String userName, String email, Wallet wallet);

    Optional<User> findUserByNameOrEmail(String username, String email);

    Optional<User> findUserBySecretKey(String secretKey);

    double getAllMoney(CurrencyName currencyName);
}
