package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.WalletName;

public interface UserService {

    User createUser(String userName, String email, Wallet wallet);

    boolean hasUser(String username, String email);

    User findUserBySecretKey(String secretKey);

    double getAllMoney(WalletName walletName);
}
