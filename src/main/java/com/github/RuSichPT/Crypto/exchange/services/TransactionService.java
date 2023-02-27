package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.Transaction;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.TransactionName;

import java.sql.Date;
import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions(Date dateFrom, Date dateTo);

    void saveTransaction(TransactionName name, String username);
}
