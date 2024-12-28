package com.github.rusichpt.crypto.exchange.repositories;

import com.github.rusichpt.crypto.exchange.repositories.entities.Currency;
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
class CurrencyRepositoryIntTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    @Sql(scripts = {"/sql/delete_tables.sql", "/sql/insert_currency.sql"})
    void shouldCorrectlyFindCurrency() {
        Optional<Currency> optCurrency = currencyRepository.findById("rub");

        Assertions.assertTrue(optCurrency.isPresent());
        Assertions.assertEquals(1, optCurrency.get().getRub());
    }
}
