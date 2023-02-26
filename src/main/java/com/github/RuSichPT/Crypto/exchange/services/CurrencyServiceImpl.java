package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.CurrencyRepository;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Currency;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final String CURRENCY = "currency";
    private final List<Currency> currencies = new ArrayList<>();
    @Autowired
    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        initCurrencies();
    }

    @Override
    public void saveCurrency(Currency currency) {
        currencyRepository.save(currency);
    }

    private void initCurrencies() {
        Currency ton = new Currency("TON", 0.000_095_64, 1.0, 180.0);
        Currency btc = new Currency("BTC", 1.0, 10_455.876, 1_882_057.716);
        Currency rub = new Currency("RUB", 5.313E-7, 0.0056, 1.0);

        Collections.addAll(currencies, ton, btc, rub);

        for (Currency c:
             currencies) {
            saveCurrency(c);
        }
    }

    @Override
    public Optional<Currency> findCurrency(JSONObject jsonObject) {
        String currency = (String) jsonObject.get(CURRENCY);

        return currencyRepository.findById(currency);
    }
}
