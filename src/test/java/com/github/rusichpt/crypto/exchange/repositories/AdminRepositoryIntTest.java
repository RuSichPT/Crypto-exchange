package com.github.rusichpt.crypto.exchange.repositories;

import com.github.rusichpt.crypto.exchange.repositories.entities.Admin;
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
class AdminRepositoryIntTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    @Sql(scripts = {"/sql/delete_tables.sql", "/sql/insert_admin.sql"})
    void shouldCorrectlyFindAdmin() {
        Optional<Admin> optAdmin = adminRepository.findById("admin");

        Assertions.assertTrue(optAdmin.isPresent());
    }
}
