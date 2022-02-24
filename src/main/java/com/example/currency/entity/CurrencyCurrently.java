package com.example.currency.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Currency_Currently")
public class CurrencyCurrently {
    @Id
    @SequenceGenerator(name = "currency3_sequence", sequenceName = "currency3_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency3_sequence")
    private Long currentlyId;
    @OneToOne()
    @JoinColumn(name = "history_id", referencedColumnName = "historyId")
    private CurrencyHistory historyEnt;
}
