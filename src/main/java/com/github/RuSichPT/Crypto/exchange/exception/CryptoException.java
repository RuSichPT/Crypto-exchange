package com.github.RuSichPT.Crypto.exchange.exception;

import com.github.RuSichPT.Crypto.exchange.exception.errors.ErrorName;

public class CryptoException extends RuntimeException {
    private final ErrorName errorName;

    public CryptoException(ErrorName errorName) {
        this.errorName = errorName;
    }

    public ErrorName getErrorName() {
        return errorName;
    }
}
