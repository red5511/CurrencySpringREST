package com.example.currency.services;

import com.example.currency.repository.CurrencyCurrentlyRepository;
import com.example.currency.repository.CurrencyHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CurrencyDataServiceTest {
    @Mock
    private CurrencyCurrentlyRepository currencyCurrentlyRepository;
    @Mock
    private CurrencyHistoryRepository currencyHistoryRepository;
    private CurrencyDataService currencyDataService;

    @BeforeEach
    void setUp(){
        currencyDataService = new CurrencyDataService(currencyHistoryRepository, currencyCurrentlyRepository);
    }
    @Test
    void shouldGetCurrentData() {
        //when
        currencyDataService.getCurrentData();
        //then
        verify(currencyCurrentlyRepository).findAll();
    }

    @Test
    void shouldGetFullData() {
        //when
        currencyDataService.getFullData();
        //then
        verify(currencyHistoryRepository).findAll();
    }
}