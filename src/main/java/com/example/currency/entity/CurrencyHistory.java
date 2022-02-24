package com.example.currency.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "CurrencyHistory")
public class CurrencyHistory {

    @Id
    @SequenceGenerator(name = "currency_history_sequence", sequenceName = "currency_history_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_history_sequence")
    private Long historyId;

    @Column(name = "currencies_names", nullable = false)
    private String currenciesName;


    @OneToOne()
    @JoinColumn(name = "currencies_name1", referencedColumnName = "currencyId")
    private Currency curr1;
    @OneToOne()
    @JoinColumn(name = "currencies_name2", referencedColumnName = "currencyId")
    private Currency curr2;

    private Double price;
    private LocalDateTime date;



}
