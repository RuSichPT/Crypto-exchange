package com.github.rusichpt2.crypto2.exchange.repositories.entities.enums;

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
