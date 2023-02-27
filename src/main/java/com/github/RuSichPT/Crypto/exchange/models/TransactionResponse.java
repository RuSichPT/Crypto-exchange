package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionResponse {
    @JsonProperty("transaction_count")
    private int transactionCount;

    public TransactionResponse(int transactionCount) {
        this.transactionCount = transactionCount;
    }
}
