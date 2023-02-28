package com.github.RuSichPT.Crypto.exchange.exception;

import com.github.RuSichPT.Crypto.exchange.exception.errors.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(CryptoException.class)
    public ResponseEntity<Error> handlerRefusalException(CryptoException exception) {
        return new ResponseEntity<>(new Error(exception.getErrorName().getMessage()), HttpStatus.BAD_REQUEST);
    }
}
