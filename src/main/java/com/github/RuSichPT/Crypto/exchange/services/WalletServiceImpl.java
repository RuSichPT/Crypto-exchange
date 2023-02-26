package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.WalletRepository;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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
    public HashMap<String, String> fillUpWallet(JSONObject jsonObject, Wallet wallet) {
        HashMap<String, String> responseMap = new HashMap<>();

        for (String key :
                jsonObject.keySet()) {
            for (CurrencyName name :
                    CurrencyName.values()) {
                if (key.equals(name.getNameWallet())) {
                    double sum = wallet.getValue(name) + jsonObject.getDouble(key);
                    wallet.setValue(name, sum);
                    responseMap.put(name.getNameWallet(), String.valueOf(sum));
                }
            }
        }

        saveWallet(wallet);

        return responseMap;
    }

    @Override
    // TODO: 26.02.2023 кастыль, переделать fillUpWallet
    public void fillUpWallet2(Wallet wallet, CurrencyName currencyName, Double count) {
        double sum = wallet.getValue(currencyName) + count;
        wallet.setValue(currencyName, sum);
        saveWallet(wallet);
    }

    @Override
    public HashMap<String, String> withdrawWallet(Wallet wallet, CurrencyName currencyName, Double count) {
        HashMap<String, String> responseMap = new HashMap<>();
        String currency = currencyName.getName();

        for (CurrencyName name :
                CurrencyName.values()) {
            if (currency.equals(name.getName())) {

                if (wallet.isEnoughMoney(name, count)) {
                    Double difference = wallet.getValue(name) - count;
                    wallet.setValue(name, difference);
                    responseMap.put(name.getNameWallet(), String.valueOf(difference));
                } else {
                    responseMap.put("text", NOT_ENOUGH);
                }
            }
        }

        saveWallet(wallet);

        return responseMap;
    }
}
