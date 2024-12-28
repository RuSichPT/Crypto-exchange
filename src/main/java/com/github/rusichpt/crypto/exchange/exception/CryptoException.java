package com.github.rusichpt.crypto.exchange.exception;

import com.github.rusichpt.crypto.exchange.exception.errors.ErrorName;

public class CryptoException extends RuntimeException {
    private final ErrorName errorName;

    public CryptoException(ErrorName errorName) {
        this.errorName = errorName;
    }

    public ErrorName getErrorName() {
        return errorName;
    }
}
