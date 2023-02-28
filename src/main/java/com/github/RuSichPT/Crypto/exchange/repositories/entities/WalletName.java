package com.github.RuSichPT.Crypto.exchange.repositories.entities;

public enum WalletName {
    BTC_WALLET("BTC_wallet"),
    TON_WALLET("TON_wallet"),
    RUB_WALLET("RUB_wallet");

    private final String name;

    WalletName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static WalletName getWalletName(CurrencyName name) {
        return WalletName.valueOf(name.getName() + "_WALLET");
    }
}
