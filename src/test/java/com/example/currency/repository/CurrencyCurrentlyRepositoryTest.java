package com.example.currency.repository;

import com.example.currency.entity.Currency;
import com.example.currency.entity.CurrencyCurrently;
import com.example.currency.entity.CurrencyHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@DataJpaTest
class CurrencyCurrentlyRepositoryTest {
    @Autowired
    private CurrencyCurrentlyRepository currencyCurrentlyRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyHistoryRepository currencyHistoryRepository;

    @Test
    public void injectedComponentsAreNotNUll(){
        assertNotNull(currencyCurrentlyRepository);
        assertNotNull(currencyRepository);
        assertNotNull(currencyHistoryRepository);

    }
    public void initData(){
        Currency currency = Currency.builder().currName("XXX").flag("...").build();
        Currency currency2 = Currency.builder().currName("YYY").flag("...").build();
        Currency currency3 = Currency.builder().currName("QQQ").flag("...").build();
        Currency currency4 = Currency.builder().currName("WWW").flag("...").build();
        currencyRepository.save(currency);
        currencyRepository.save(currency2);
        currencyRepository.save(currency3);
        currencyRepository.save(currency4);
        CurrencyHistory currencyHistory = CurrencyHistory.builder().curr1(currency).curr2(currency2).currenciesName("XXXYYY").build();
        CurrencyHistory currencyHistory2 = CurrencyHistory.builder().curr1(currency3).curr2(currency4).currenciesName("QQQWWW").build();
        currencyHistoryRepository.save(currencyHistory);
        currencyHistoryRepository.save(currencyHistory2);
        CurrencyCurrently currencyCurrently = CurrencyCurrently.builder().historyEnt(currencyHistory).build();
        currencyCurrentlyRepository.save(currencyCurrently);

    }
    @Test
    public void shouldUpdateHistoryEntId() throws Exception {
        //given
        initData();
        //when
        currencyCurrentlyRepository.updateHistoryEnt(1L, 2L);
        //then
        CurrencyCurrently currencyCurrently = currencyCurrentlyRepository.getById(1L);

        assertEquals(2L, currencyCurrently.getHistoryEnt().getHistoryId());
    }

}