package com.github.RuSichPT.Crypto.exchange.repositories;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
