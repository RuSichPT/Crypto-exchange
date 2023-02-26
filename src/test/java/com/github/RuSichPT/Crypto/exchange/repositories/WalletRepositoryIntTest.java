package com.github.RuSichPT.Crypto.exchange.repositories;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
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
public class WalletRepositoryIntTest {

    @Autowired
    private WalletRepository walletRepository;

    @Test
    @Sql(scripts = {"/sql/delete_tables.sql", "/sql/insert_wallet.sql"})
    public void shouldCorrectlyUpdateWallet() {
        Integer id = 1;
        Double rub = 1500.0;
        Wallet newWallet = new Wallet();
        newWallet.setId(id);
        newWallet.setRub(rub);

        walletRepository.save(newWallet);
        Optional<Wallet> optionalWallet = walletRepository.findById(id);

        Assertions.assertTrue(optionalWallet.isPresent());
        Assertions.assertEquals(newWallet.getRub(), rub);

    }
}
