package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.Currency;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;

public interface CurrencyService {

    void saveCurrency(Currency currency);

    Currency findCurrency(CurrencyName name);

    Double exchangeCurrency(CurrencyName currencyFrom, CurrencyName currencyTo, Double amountFrom);

    void changeCurrency(CurrencyName baseCurrency, CurrencyName CurrencyToChange, Double value);

}
