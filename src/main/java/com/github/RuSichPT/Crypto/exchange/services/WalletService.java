package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.exception.CryptoException;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.WalletName;

public interface WalletService {

    Integer saveWallet(Wallet wallet);

    Wallet createWallet();

    void fillUpWallet(Wallet wallet, WalletName name, Double value);

    void withdrawWallet(Wallet wallet, WalletName name, Double value) throws CryptoException;

}
