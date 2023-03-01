package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.exception.CryptoException;
import com.github.RuSichPT.Crypto.exchange.exception.errors.ErrorName;
import com.github.RuSichPT.Crypto.exchange.repositories.CurrencyRepository;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Currency;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.enums.CurrencyName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        for (Currency c :
                currencies) {
            saveCurrency(c);
        }
    }

    @Override
    public Currency findCurrency(CurrencyName name) {
        Optional<Currency> optCurrency = currencyRepository.findById(name.getName());
        if (optCurrency.isEmpty()) {
            throw new CryptoException(ErrorName.NOT_VALID_SECRET_KEY);
        }
        return optCurrency.get();
    }

    @Override
    public Double exchangeCurrency(CurrencyName currencyFrom, CurrencyName currencyTo, Double amountFrom) {
        Optional<Currency> optCurrency = currencyRepository.findById(currencyFrom.getName());

        if (optCurrency.isPresent()) {
            Currency currency = optCurrency.get();

            Double currencyToValue = currency.getValue(currencyTo);

            return currencyToValue * amountFrom;

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void changeCurrency(CurrencyName baseCurrency, CurrencyName CurrencyToChange, Double value) {
        Optional<Currency> optCurrency = currencyRepository.findById(baseCurrency.getName());
        if (optCurrency.isPresent()) {
            Currency currency = optCurrency.get();
            currency.setValue(CurrencyToChange, value);
            currencyRepository.save(currency);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
