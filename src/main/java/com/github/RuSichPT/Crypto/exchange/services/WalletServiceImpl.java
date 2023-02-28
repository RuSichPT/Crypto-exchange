package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.WalletRepository;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.WalletName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    private static final String NOT_ENOUGH = "Недостаточно средств для снятия";

    @Autowired
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

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
    public void withdrawWallet(Wallet wallet, WalletName name, Double value) {
        if (wallet.isEnoughMoney(name, value)) {
            double difference = wallet.getValue(name) - value;
            wallet.setValue(name, difference);
        } else {
            // TODO: 28.02.2023 исключ NOT_ENOUGH
        }
        saveWallet(wallet);
    }
}
