package com.example.currency.repository;

import com.example.currency.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    //Optional<Currency> findCurrencyBycurrency_name(String currenciesName);
    Currency findBycurrName(String Currency_name);

}
