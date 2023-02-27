package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import lombok.Getter;

@Getter
public class SumRequest {
    @JsonProperty("secret_key")
    private String secretKey;

    @JsonProperty("currency")
    private CurrencyName currency;
}
