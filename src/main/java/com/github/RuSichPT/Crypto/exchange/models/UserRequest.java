package com.github.RuSichPT.Crypto.exchange.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserRequest {

    @JsonProperty("username")
    private String userName;

    private String email;
}
