package com.github.RuSichPT.Crypto.exchange.exception;

import com.github.RuSichPT.Crypto.exchange.exception.errors.ErrorName;
import lombok.Data;

@Data // todo Warning:(6, 1) Generating equals/hashCode implementation but without a call to superclass, even though this class does not extend java.lang.Object. If this is intentional, add '(callSuper=false)' to your type.
public class CryptoException extends RuntimeException {
    private final ErrorName errorName;
}
