package com.github.rusichpt.crypto.exchange.services;

import com.github.rusichpt.crypto.exchange.exception.CryptoException;
import com.github.rusichpt.crypto.exchange.repositories.entities.User;
import com.github.rusichpt.crypto.exchange.repositories.entities.Wallet;
import com.github.rusichpt.crypto.exchange.repositories.entities.enums.CurrencyName;

import java.util.Map;

public interface UserService {

    User createUser(String userName, String email, Wallet wallet) throws CryptoException;

    boolean hasUser(String username, String email);

    boolean hasUser(String secretKey);

    User findUserBySecretKey(String secretKey) throws CryptoException;

    Map<CurrencyName, Double> getAllMoney(CurrencyName name);
}
