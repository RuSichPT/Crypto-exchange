package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import lombok.Getter;

@Getter
public class WithdrawRequest {

    @JsonProperty("secret_key")
    String secretKey;

    @JsonProperty("currency")
    CurrencyName currency;

    @JsonProperty("count")
    Double count;

    @JsonProperty("credit_card")
    String creditCard;

    @JsonProperty("wallet")
    String walletAddress;
}
