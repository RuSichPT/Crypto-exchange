package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.TransactionRepository;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Transaction;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.TransactionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getTransactions(Date dateFrom, Date dateTo) {
        List<Transaction> transactions = transactionRepository.findAll();
        List<Transaction> filtered = new ArrayList<>();

        for (Transaction tr :
                transactions) {
            Date date = tr.getDate();
            if (date.compareTo(dateFrom) >= 0 && date.compareTo(dateTo) <= 0) {
                filtered.add(tr);
            }
        }
        return filtered;
    }

    @Override
    public void saveTransaction(TransactionName name, String username) {
        Transaction transaction = new Transaction(name.getName(), username);
        transactionRepository.save(transaction);
    }
}
