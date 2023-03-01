package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.enums.CurrencyName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeResponse {
    @JsonProperty("currency_from")
    private CurrencyName currencyFrom;

    @JsonProperty("currency_to")
    private CurrencyName currencyTo;

    @JsonProperty("amount_from")
    private Double amountFrom;

    @JsonProperty("amount_to")
    private Double amountTo;
}
