package com.github.rusichpt.crypto.exchange.services;

import com.github.rusichpt.crypto.exchange.exception.CryptoException;
import com.github.rusichpt.crypto.exchange.repositories.entities.Wallet;
import com.github.rusichpt.crypto.exchange.repositories.entities.enums.WalletName;

public interface WalletService {

    Integer saveWallet(Wallet wallet);

    Wallet createWallet();

    void fillUpWallet(Wallet wallet, WalletName name, Double value);

    void withdrawWallet(Wallet wallet, WalletName name, Double value) throws CryptoException;

}
