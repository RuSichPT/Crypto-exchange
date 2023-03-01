package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.enums.WalletName;
import lombok.Data;

@Data
public class FillUpRequest {
    @JsonProperty("secret_key")
    private String secretKey;

    @JsonProperty("wallet_name")
    private WalletName walletName;

    @JsonProperty("value")
    private Double value;
}
