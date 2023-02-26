package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.Currency;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import org.json.JSONObject;

import java.util.Optional;

public interface CurrencyService {

    void saveCurrency(Currency currency);

    Optional<Currency> findCurrency(JSONObject jsonObject);

    Double exchangeCurrency(CurrencyName currencyFrom, CurrencyName currencyTo, Double amountFrom);

}
