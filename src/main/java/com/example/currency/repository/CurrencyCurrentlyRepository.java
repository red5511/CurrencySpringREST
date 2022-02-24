package com.example.currency.repository;

import com.example.currency.entity.CurrencyCurrently;
import com.example.currency.entity.CurrencyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CurrencyCurrentlyRepository extends JpaRepository<CurrencyCurrently, Long> {
    CurrencyCurrently findByHistoryEntCurrenciesName(String currName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
            value = "Update currency_currently set history_id=?2 where currently_id = ?1",
            nativeQuery = true
    )
    int updateHistoryEnt(Long currID, Long histID);

}
