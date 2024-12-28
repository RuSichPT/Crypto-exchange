package com.github.rusichpt.crypto.exchange.services;

import com.github.rusichpt.crypto.exchange.exception.CryptoException;
import com.github.rusichpt.crypto.exchange.repositories.entities.Currency;
import com.github.rusichpt.crypto.exchange.repositories.entities.enums.CurrencyName;

public interface CurrencyService {

    void saveCurrency(Currency currency);

    Currency findCurrency(CurrencyName name) throws CryptoException;

    Double exchangeCurrency(CurrencyName currencyFrom, CurrencyName currencyTo, Double amountFrom);

    void changeCurrency(CurrencyName baseCurrency, CurrencyName CurrencyToChange, Double value);

}
