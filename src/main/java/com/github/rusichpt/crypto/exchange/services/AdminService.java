package com.github.rusichpt.crypto.exchange.services;

import com.github.rusichpt.crypto.exchange.exception.CryptoException;
import com.github.rusichpt.crypto.exchange.repositories.entities.Admin;

public interface AdminService {

    Admin createAdmin(String userName, String email);

    Admin findAdminBySecretKey(String secretKey) throws CryptoException;

    boolean isAdmin(String secretKey);
}
