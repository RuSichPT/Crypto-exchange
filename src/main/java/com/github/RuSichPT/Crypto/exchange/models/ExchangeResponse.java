package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import lombok.Data;

@Data
public class ExchangeResponse {

    @JsonProperty("currency_from")
    private CurrencyName currencyFrom;

    @JsonProperty("currency_to")
    private CurrencyName currencyTo;

    @JsonProperty("amount_from")
    private Double amountFrom;

    @JsonProperty("amount_to")
    private Double amountTo;

    public ExchangeResponse(CurrencyName currencyFrom, CurrencyName currencyTo, Double amountFrom, Double amountTo) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amountFrom = amountFrom;
        this.amountTo = amountTo;
    }
}
