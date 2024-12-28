package com.github.rusichpt2.crypto2.exchange.services;

import com.github.rusichpt2.crypto2.exchange.exception.CryptoException;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.enums.CurrencyName;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.User;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.Wallet;

import java.util.Map;

public interface UserService {

    User createUser(String userName, String email, Wallet wallet) throws CryptoException;

    boolean hasUser(String username, String email);

    boolean hasUser(String secretKey);

    User findUserBySecretKey(String secretKey) throws CryptoException;

    Map<CurrencyName, Double> getAllMoney(CurrencyName name);
}
