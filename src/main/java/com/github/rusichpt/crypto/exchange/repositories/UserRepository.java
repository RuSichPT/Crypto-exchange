package com.github.rusichpt.crypto.exchange.repositories;

import com.github.rusichpt.crypto.exchange.repositories.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserNameOrEmail(String userName, String Email);

    Optional<User> findBySecretKey(String secretKey);


}
