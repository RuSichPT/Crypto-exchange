package com.github.rusichpt2.crypto2.exchange.repositories;

import com.github.rusichpt2.crypto2.exchange.repositories.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}