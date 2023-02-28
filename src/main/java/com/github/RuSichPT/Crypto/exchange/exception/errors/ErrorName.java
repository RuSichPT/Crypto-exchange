package com.github.RuSichPT.Crypto.exchange.exception.errors;

public enum ErrorName {
    REFUSAL("Такой пользователь уже существует. Отказ в регистрации!"),
    NOT_VALID_SECRET_KEY("Секретный ключ недействительный"),
    NOT_FOUND_CURRENCY("Такой валюты не существует");

    private final String message;

    ErrorName(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
