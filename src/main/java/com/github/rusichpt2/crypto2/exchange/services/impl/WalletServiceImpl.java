package com.github.rusichpt2.crypto2.exchange.services.impl;

import com.github.rusichpt2.crypto2.exchange.exception.CryptoException;
import com.github.rusichpt2.crypto2.exchange.repositories.WalletRepository;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.Wallet;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.enums.WalletName;
import com.github.rusichpt2.crypto2.exchange.services.WalletService;
import com.github.rusichpt2.crypto2.exchange.exception.errors.ErrorName;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    @Autowired
    private final WalletRepository walletRepository;

    @Override
    public Integer saveWallet(Wallet wallet) {
        return walletRepository.save(wallet).getId();
    }

    @Override
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        Integer id = saveWallet(wallet);
        wallet.setId(id);

        return wallet;
    }

    @Override
    public void fillUpWallet(Wallet wallet, WalletName name, Double value) {
        double sum = wallet.getValue(name) + value;
        wallet.setValue(name, sum);
        saveWallet(wallet);
    }

    @Override
    public void withdrawWallet(Wallet wallet, WalletName name, Double value) throws CryptoException {
        if (wallet.isEnoughMoney(name, value)) {
            double difference = wallet.getValue(name) - value;
            wallet.setValue(name, difference);
        } else {
            throw new CryptoException(ErrorName.NOT_ENOUGH);
        }
        saveWallet(wallet);
    }
}
