package com.example.currency.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
@AllArgsConstructor
@Builder
public class Exchange{
    private Double amount, price, exchanged;
    private String curr1, curr2;

}