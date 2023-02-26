package com.github.RuSichPT.Crypto.exchange.repositories.entities;

import java.util.ArrayList;
import java.util.Collections;

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

    public String getNameWallet() {
        return name + "_wallet";
    }

    public static ArrayList<CurrencyName> asList() {
        ArrayList<CurrencyName> list = new ArrayList<>();

        Collections.addAll(list, values());

        return list;
    }
}
