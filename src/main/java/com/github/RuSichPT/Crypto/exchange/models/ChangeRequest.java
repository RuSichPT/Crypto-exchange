package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import lombok.Data;

import java.util.Map;

@Data
public class ChangeRequest {
    @JsonProperty("secret_key")
    private String secretKey;

    @JsonProperty("base_currency")
    private CurrencyName baseCurrency;

    Map<CurrencyName, Double> currencies;
}
