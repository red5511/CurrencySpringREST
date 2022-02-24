package com.example.currency.repository;

import com.example.currency.entity.CurrencyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyHistoryRepository extends JpaRepository<CurrencyHistory, Long> {
}
