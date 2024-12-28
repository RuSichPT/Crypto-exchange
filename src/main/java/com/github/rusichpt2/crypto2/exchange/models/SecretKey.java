package com.github.rusichpt2.crypto2.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SecretKey {
    @JsonProperty("secret_key")
    private final String value;
}