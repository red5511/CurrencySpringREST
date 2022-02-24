package com.example.currency.controllers;

import com.example.currency.entity.Currency;
import com.example.currency.entity.CurrencyCurrently;
import com.example.currency.entity.CurrencyHistory;
import com.example.currency.entity.Exchange;
import com.example.currency.services.CurrencyDataService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@RestController // ResetController konwertuje odpowiedzin do foramtu json7
@RequestMapping(path = "/")
public class CurrencyController {
    @Autowired
    CurrencyDataService currencyDataService;
    @GetMapping("/")
    public String home(){
        //model.addAttribute("currData", currencyDataService.getCurrentData());
        return "Hello Word \n";
    }
    @GetMapping("/api")
    public List<CurrencyCurrently> apiMain(){
        //model.addAttribute("currData", currencyDataService.getCurrentData());
        return currencyDataService.getCurrentData();
    }
    @GetMapping("/api/full_info")
    public List<CurrencyHistory> fullInfo(){
        return currencyDataService.getFullData();
    }
    @GetMapping("/api/{curr1}/{curr2}/{amount}") // np /api/USD/PLN/30
    public Exchange testing(@PathVariable String curr1, @PathVariable String curr2, @PathVariable Double amount){
        double price =  currencyDataService.getPrice(curr1, curr2);
        return Exchange.builder().curr1(curr1).curr2(curr2).amount(amount).price(price)
                .exchanged(new BigDecimal(amount * price).setScale(2, RoundingMode.HALF_UP).doubleValue()).build();
    }

}

