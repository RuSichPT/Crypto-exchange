package com.github.rusichpt.crypto.exchange.repositories.entities.enums;

public enum CurrencyName {
    BTC("BTC"),
    TON("TON"),
    RUB("RUB");
    private final String name;

    CurrencyName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
