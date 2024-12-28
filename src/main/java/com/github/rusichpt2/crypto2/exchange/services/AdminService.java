package com.github.rusichpt2.crypto2.exchange.services;

import com.github.rusichpt2.crypto2.exchange.exception.CryptoException;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.Admin;

public interface AdminService {

    Admin createAdmin(String userName, String email);

    Admin findAdminBySecretKey(String secretKey) throws CryptoException;

    boolean isAdmin(String secretKey);
}
