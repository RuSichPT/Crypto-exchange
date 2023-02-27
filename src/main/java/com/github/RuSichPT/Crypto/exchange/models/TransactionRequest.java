package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.sql.Date;

@Getter
public class TransactionRequest {
    @JsonProperty("secret_key")
    private String secretKey;

    @JsonProperty("date_from")
    private Date dateFrom;

    @JsonProperty("date_to")
    private Date dateTo;
}
