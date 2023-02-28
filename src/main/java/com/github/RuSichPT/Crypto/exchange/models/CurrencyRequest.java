package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import lombok.Data;

@Data
public class CurrencyRequest {
    @JsonProperty("secret_key")
    private String secretKey;

    @JsonProperty("currency")
    private CurrencyName currencyName;
}
