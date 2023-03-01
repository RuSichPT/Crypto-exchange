package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.exception.CryptoException;
import com.github.RuSichPT.Crypto.exchange.repositories.WalletRepository;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.WalletName;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.github.RuSichPT.Crypto.exchange.exception.errors.ErrorName.NOT_ENOUGH;

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
            throw new CryptoException(NOT_ENOUGH);
        }
        saveWallet(wallet);
    }
}
