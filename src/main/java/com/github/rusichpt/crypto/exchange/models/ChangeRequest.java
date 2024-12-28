package com.github.rusichpt.crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.rusichpt.crypto.exchange.repositories.entities.enums.CurrencyName;
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
