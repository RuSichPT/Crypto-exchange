package com.github.rusichpt.crypto.exchange.repositories;

import com.github.rusichpt.crypto.exchange.repositories.entities.User;
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
class UserRepositoryIntTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql(scripts = {"/sql/delete_tables.sql", "/sql/insert_wallet.sql", "/sql/insert_user.sql"})
    void shouldCorrectlyFindUser() {
        String username = "vasya_vezunchik";
        Double rub = 3500.0;
        Optional<User> optionalUser = userRepository.findById(username);

        Assertions.assertTrue(optionalUser.isPresent());
        Assertions.assertEquals(rub, optionalUser.get().getWallet().getRub());
    }

    @Test
    @Sql(scripts = {"/sql/delete_tables.sql", "/sql/insert_wallet.sql", "/sql/insert_user.sql"})
    void shouldCorrectlyFindUserBySecretKey() {
        String secretKey = "AAFeyWzOnlD-9G4i662PdKn2B-b4BwrCNA";

        Optional<User> optionalUser = userRepository.findBySecretKey(secretKey);

        Assertions.assertTrue(optionalUser.isPresent());
        Assertions.assertEquals(secretKey, optionalUser.get().getSecretKey());
    }

}
