package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.enums.CurrencyName;
import lombok.Data;

@Data
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
