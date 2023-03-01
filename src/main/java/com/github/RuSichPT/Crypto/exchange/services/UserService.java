package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.exception.CryptoException;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;

import java.util.Map;

public interface UserService {

    User createUser(String userName, String email, Wallet wallet) throws CryptoException;

    boolean hasUser(String username, String email);

    boolean hasUser(String secretKey);

    User findUserBySecretKey(String secretKey) throws CryptoException;

    Map<CurrencyName, Double> getAllMoney(CurrencyName name);
}
