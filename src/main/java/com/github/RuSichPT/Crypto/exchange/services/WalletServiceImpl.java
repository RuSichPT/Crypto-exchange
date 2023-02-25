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

    private static final String CURRENCY = "currency";
    private static final String COUNT = "count";

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
    public HashMap<String, String> withdrawWallet(JSONObject jsonObject, Wallet wallet) {
        HashMap<String, String> responseMap = new HashMap<>();
        String currency = (String) jsonObject.get(CURRENCY);
        String count = (String) jsonObject.get(COUNT);

        for (CurrencyName name :
                CurrencyName.values()) {
            if (currency.equals(name.getName())) {
                Double difference = wallet.getValue(name) - Double.parseDouble(count);
                if (difference >= 0) {
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
