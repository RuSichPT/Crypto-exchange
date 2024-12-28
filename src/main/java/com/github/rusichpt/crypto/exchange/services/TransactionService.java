package com.github.rusichpt.crypto.exchange.services;

import com.github.rusichpt.crypto.exchange.repositories.entities.Transaction;
import com.github.rusichpt.crypto.exchange.repositories.entities.enums.TransactionName;

import java.sql.Date;
import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions(Date dateFrom, Date dateTo);

    void saveTransaction(TransactionName name, String username);
}
