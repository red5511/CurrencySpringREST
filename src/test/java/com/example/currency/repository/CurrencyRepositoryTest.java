package com.example.currency.repository;

import com.example.currency.entity.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CurrencyRepositoryTest {

    @Autowired
    CurrencyRepository currencyRepository;
    @Test
    public void shouldFindCurrencyByNameWhenExist(){

        String name = "XXX";
        Currency currency = Currency.builder().currName(name).build();
        currencyRepository.save(currency);

        assertNotNull(currencyRepository.findBycurrName(name));
    }
    @Test
    public void shouldFindCurrencyByNameWhenNotExist(){

        String name = "XXX";
        assertNull(currencyRepository.findBycurrName(name));
    }
}