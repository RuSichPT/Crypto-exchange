package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SecretKey {

    @JsonProperty("secret_key")
    private String value;

    public SecretKey(String value) {
        this.value = value;
    }

    public SecretKey() {
    }
}
