package com.example.currency.services;

import com.example.currency.entity.Currency;
import com.example.currency.entity.CurrencyCurrently;
import com.example.currency.entity.CurrencyHistory;
import com.example.currency.repository.CurrencyCurrentlyRepository;
import com.example.currency.repository.CurrencyHistoryRepository;
import com.example.currency.repository.CurrencyRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class InitService {
    private static String url = "https://tradingeconomics.com/currencies";
    private Map<String, String> currency_dict = new HashMap<String, String>();
    static boolean firstRun = true;
    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyHistoryRepository currencyHistoryRepository;

    @Autowired
    private CurrencyCurrentlyRepository currencyCurrentlyRepository;

    @PostConstruct
    @Scheduled(cron = "0 */35 * * * *")
    public void scrapData() throws Exception {


        try{
            final Document document = Jsoup.connect(url).get();
            //System.out.println(document.outerHtml());

            for (Element row : document.select("table.table-hover.table-striped.table-heatmap tr")){
                String curr_1_2 = row.select(".datatable-item-first").text();
                String price = row.select("[id=p]").text();


                System.out.println(curr_1_2 + " " + price);

                if (!curr_1_2.isEmpty() && curr_1_2.length() == 6) currency_dict.put(curr_1_2, price.replaceAll(",", ""));
                // ##th.te-sort:nth-of-type(2)
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if (firstRun){
            initCurrency();
        }
        saveToDatabase();
        firstRun = false;

    }
    public  void initCurrency(){
        for (Map.Entry<String, String> entry : currency_dict.entrySet()) {
            String key = entry.getKey();
            Currency curr1 = Currency.builder().currName(key.substring(0, 3)).flag("xxx").build();
            Currency curr2 = Currency.builder().currName(key.substring(3)).flag("xxx").build();

            try {
                currencyRepository.save(curr1);
                //currencyRepository.save(curr2);
            }catch (Exception e){

                //System.out.println("error");
            }
            try {
                currencyRepository.save(curr2);
            }catch (Exception e){}

        }
    }
    public void saveToDatabase(){

        System.out.println("Lenght " + currency_dict.size());
        Boolean buf = true;
        CurrencyHistory currencyHistoryBuf = null;
        for (Map.Entry<String, String> entry : currency_dict.entrySet()) {

            String key = entry.getKey();
            String val = entry.getValue();
            Currency curr1 = currencyRepository.findBycurrName(key.substring(0, 3));
            Currency curr2 = currencyRepository.findBycurrName(key.substring(3));

            if (buf){
                currencyHistoryBuf = CurrencyHistory.builder().curr1(curr1).curr2(curr2).currenciesName(key).price(Double.parseDouble(val)).date(LocalDateTime.now()).build();
                buf = false;
            }

            System.out.println("Printing " + curr1);
            //Currency curr2 = Currency.builder().CurrencyName(key.substring(3)).flag("xxx").build();

            CurrencyHistory currencyHistory = CurrencyHistory.builder().curr1(curr1).curr2(curr2).currenciesName(key).price(Double.parseDouble(val)).date(LocalDateTime.now()).build();
            //new CurrencyHistory(-1L, key, curr1, curr2, Double.parseDouble(val), LocalDateTime.now());
            System.out.println("Printing2 " + currencyHistory);
            try {
                currencyHistoryRepository.save(currencyHistory);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (firstRun){
                CurrencyCurrently currCurrently = CurrencyCurrently.builder().historyEnt(currencyHistory).build();
                try {
                    currencyCurrentlyRepository.save(currCurrently);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                CurrencyCurrently test = currencyCurrentlyRepository.findByHistoryEntCurrenciesName(currencyHistory.getCurrenciesName());

                currencyCurrentlyRepository.updateHistoryEnt(test.getCurrentlyId(), currencyHistory.getHistoryId());
                //CurrencyCurrently test2 = currencyCurrentlyRepository.findByHistoryEntCurrenciesName(currencyHistory.getCurrenciesName());

            }

        }

        firstRun = false;

    }
}
