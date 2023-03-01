package com.github.RuSichPT.Crypto.exchange.advice;

import com.github.RuSichPT.Crypto.exchange.exception.CryptoException;
import com.github.RuSichPT.Crypto.exchange.exception.errors.Error;
import com.github.RuSichPT.Crypto.exchange.exception.errors.ErrorName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CryptoException.class)
    public ResponseEntity<Error> handlerCryptoException(CryptoException exception) {
        if (exception.getErrorName() == ErrorName.FORBIDDEN)
            return new ResponseEntity<>(new Error(exception.getErrorName().getMessage()), HttpStatus.FORBIDDEN);
        else
            return new ResponseEntity<>(new Error(exception.getErrorName().getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handlerHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(new Error(ErrorName.NOT_FOUND_WALLET.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Error> handlerNullPointerExceptionException(NullPointerException exception) {
        return new ResponseEntity<>(new Error(ErrorName.SYNTAX_ERROR.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
