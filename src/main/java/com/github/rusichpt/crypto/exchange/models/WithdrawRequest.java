package com.github.rusichpt.crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.rusichpt.crypto.exchange.repositories.entities.enums.WalletName;
import lombok.Data;

@Data
public class WithdrawRequest {
    @JsonProperty("secret_key")
    private String secretKey;

    @JsonProperty("wallet_name")
    private WalletName walletName;

    @JsonProperty("value")
    private Double value;

    @JsonProperty("credit_card")
    private String creditCard;

    @JsonProperty("wallet")
    private String walletAddress;
}
