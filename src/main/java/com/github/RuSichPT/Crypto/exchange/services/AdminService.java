package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.exception.CryptoException;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Admin;

public interface AdminService {

    Admin createAdmin(String userName, String email);

    Admin findAdminBySecretKey(String secretKey) throws CryptoException;

    boolean isAdmin(String secretKey);
}
