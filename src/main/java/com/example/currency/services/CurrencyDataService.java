package com.example.currency.services;

import com.example.currency.entity.Currency;
import com.example.currency.entity.CurrencyCurrently;
import com.example.currency.entity.CurrencyHistory;
import com.example.currency.repository.CurrencyCurrentlyRepository;
import com.example.currency.repository.CurrencyHistoryRepository;
import com.example.currency.repository.CurrencyRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDataService {
    @Autowired
    private CurrencyHistoryRepository currencyHistoryRepository;
    @Autowired
    private CurrencyCurrentlyRepository currencyCurrentlyRepository;

    public Double getPrice(String curr1, String curr2) {
        CurrencyCurrently buf = currencyCurrentlyRepository.findByHistoryEntCurrenciesName(curr1.toUpperCase() + curr2.toUpperCase());
        return buf.getHistoryEnt().getPrice();
    }

    public List<CurrencyCurrently> getCurrentData() {
        return currencyCurrentlyRepository.findAll();
    }

    public List<CurrencyHistory> getFullData() {
        return currencyHistoryRepository.findAll();
    }
}
