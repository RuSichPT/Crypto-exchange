package com.github.rusichpt.crypto.exchange.exception.errors;

public enum ErrorName {
    REFUSAL("Такой пользователь уже существует. Отказ в регистрации!"),
    NOT_VALID_SECRET_KEY("Секретный ключ недействительный"),
    NOT_FOUND_CURRENCY("Такой валюты не существует"),
    NOT_FOUND_WALLET("Такого кошелька не существует"),
    SYNTAX_ERROR("Ошибка синтаксиса"),
    NOT_ENOUGH("Недостаточно средств на кошельке"),
    FORBIDDEN("Доступ запрещен");

    private final String message;

    ErrorName(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
