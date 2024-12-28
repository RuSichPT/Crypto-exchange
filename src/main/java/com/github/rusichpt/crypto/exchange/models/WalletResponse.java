package com.github.rusichpt.crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.rusichpt.crypto.exchange.repositories.entities.enums.WalletName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletResponse {
    @JsonProperty("wallet_name")
    WalletName walletName;

    @JsonProperty("value")
    Double value;
}
