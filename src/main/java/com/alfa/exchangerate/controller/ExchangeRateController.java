package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.client.OpenExchangeClient;
import com.alfa.exchangerate.config.OpenExchangeConfig;
import com.alfa.exchangerate.model.HistoricalRates;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class ExchangeRateController {
    private final OpenExchangeClient openExchangeClient;
    private final OpenExchangeConfig openExchangeConfig;

    public ExchangeRateController(OpenExchangeClient client,
                                  OpenExchangeConfig config) {
        openExchangeClient = client;
        openExchangeConfig = config;
    }

    @GetMapping("/latest")
    public ResponseEntity<Object> getLatestRate() {
        try {
            ;
            return new ResponseEntity<>(openExchangeClient
                    .getLatestRate(openExchangeConfig.getAppId(), openExchangeConfig.getBase()), HttpStatus.OK);
        } catch (FeignException feignEx) {
            return new ResponseEntity<>(feignEx.contentUTF8(),
                    HttpStatus.valueOf(feignEx.status()));
        }
    }

//    @GetMapping("/{date}")
//    public ResponseEntity<Object> getRateByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date) {
//        try {
//            return new ResponseEntity<>(openExchangeClient
//                    .getRateByDate(date, openExchangeConfig.getAppId(), openExchangeConfig.getBase()), HttpStatus.OK);
//        } catch (FeignException feignEx) {
//            return new ResponseEntity<>(feignEx.contentUTF8(),
//                    HttpStatus.valueOf(feignEx.status()));
//        }
//    }

    @GetMapping("/difference-in-rate/{currencyCode}")
    public ResponseEntity<Object> checkDifferenceInRate(@PathVariable String currencyCode) {
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new Date());
            calendar.add(Calendar.DATE, -1);
            String yesterday = dateFormat.format(calendar.getTime());
            System.out.println(yesterday);

            HistoricalRates yesterdayRate = openExchangeClient
                    .getRateByDate(yesterday, openExchangeConfig.getAppId(), openExchangeConfig.getBase(), currencyCode)
                    .getBody();
            HistoricalRates currentRate = openExchangeClient
                    .getRateByDate(yesterday, openExchangeConfig.getAppId(), openExchangeConfig.getBase(), currencyCode)
                    .getBody();

            return new ResponseEntity<>(currentRate, HttpStatus.OK);
        } catch (FeignException feignEx) {
            return new ResponseEntity<>(feignEx.contentUTF8() == null ? feignEx.getMessage() : feignEx.contentUTF8(),
                    HttpStatus.valueOf(feignEx.status()));
        }
    }
}
