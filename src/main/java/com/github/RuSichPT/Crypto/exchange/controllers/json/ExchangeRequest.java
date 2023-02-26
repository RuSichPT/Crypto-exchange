package com.github.RuSichPT.Crypto.exchange.controllers.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import lombok.Getter;

@Getter
public class ExchangeRequest {

    @JsonProperty("secret_key")
    private String secretKey;

    @JsonProperty("currency_from")
    private CurrencyName currencyFrom;

    @JsonProperty("currency_to")
    private CurrencyName currencyTo;

    @JsonProperty("amount")
    private Double amountFrom;
}
