package com.github.RuSichPT.Crypto.exchange.repositories.entities;

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
