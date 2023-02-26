package com.github.RuSichPT.Crypto.exchange.controllers.json;

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
