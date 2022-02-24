package com.example.currency.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "Currency", uniqueConstraints={@UniqueConstraint(columnNames = {"currName"})})
public class Currency {

    @Id
    @SequenceGenerator(name = "currency2_sequence", sequenceName = "currency2_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency2_sequence")
    private Long currencyId;
    @Column(name = "currName", nullable = false)
    private String currName;
    private String flag;

}
