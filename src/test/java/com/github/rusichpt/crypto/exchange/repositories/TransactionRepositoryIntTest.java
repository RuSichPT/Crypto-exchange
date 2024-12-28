package com.github.rusichpt.crypto.exchange.repositories;

import com.github.rusichpt.crypto.exchange.repositories.entities.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class TransactionRepositoryIntTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    @Sql(scripts = {"/sql/delete_tables.sql", "/sql/insert_transaction.sql"})
    public void shouldCorrectlyFindTransaction() {
        Optional<Transaction> optTransaction = transactionRepository.findById(1);

        Assertions.assertTrue(optTransaction.isPresent());
        Assertions.assertEquals(optTransaction.get().getName(), "add");
    }
}
