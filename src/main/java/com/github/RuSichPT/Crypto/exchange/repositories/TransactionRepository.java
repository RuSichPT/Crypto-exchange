package com.github.RuSichPT.Crypto.exchange.repositories;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}