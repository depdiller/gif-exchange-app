package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.client.GiphyClient;
import com.alfa.exchangerate.client.OpenExchangeClient;
import com.alfa.exchangerate.config.GiphyConfig;
import com.alfa.exchangerate.config.OpenExchangeConfig;
import com.alfa.exchangerate.model.HistoricalRates;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api")
@Validated
public class AppController {
    private final OpenExchangeClient openExchangeClient;
    private final GiphyClient giphyClient;
    private final OpenExchangeConfig openExchangeConfig;
    private final GiphyConfig giphyConfig;
    private final Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public AppController(OpenExchangeClient openExchangeClient,
                         GiphyClient giphyClient,
                         OpenExchangeConfig exchangeConfig,
                         GiphyConfig giphyConfig) {
        this.giphyClient = giphyClient;
        this.openExchangeClient = openExchangeClient;
        openExchangeConfig = exchangeConfig;
        this.giphyConfig = giphyConfig;
    }

//    @GetMapping("/latest")
//    public ResponseEntity<Object> getLatestRate() {
//        try {
//            ;
//            return new ResponseEntity<>(openExchangeClient
//                    .getLatestRate(openExchangeConfig.getAppId(), openExchangeConfig.getBase()), HttpStatus.OK);
//        } catch (FeignException feignEx) {
//            return new ResponseEntity<>(feignEx.contentUTF8(),
//                    HttpStatus.valueOf(feignEx.status()));
//        }
//    }

    @GetMapping("/gif/{tag}")
    public ResponseEntity<Object> getRandomGif(@PathVariable String tag) {
        ResponseEntity<Object> giphyRes = giphyClient.getGif(giphyConfig.getApiKey(), tag);
        return new ResponseEntity<>(giphyRes.getBody(), HttpStatus.valueOf(giphyRes.getStatusCodeValue()));
    }

    @GetMapping("/difference-in-rate/{currencyCode}")
    public ResponseEntity<Object> checkDifferenceInRate(@PathVariable @Size(max = 3, min = 3) String currencyCode) {
        String currentDate = dateFormat.format(new Date());
        calendar.add(Calendar.DATE, -1);
        String yesterday = dateFormat.format(calendar.getTime());

        ResponseEntity<HistoricalRates> yesterdayRateResponse = openExchangeClient
                .getRateByDate(currentDate, openExchangeConfig.getAppId(), openExchangeConfig.getBase(), currencyCode);
        ResponseEntity<HistoricalRates> currentRateResponse = openExchangeClient
                .getRateByDate(yesterday, openExchangeConfig.getAppId(), openExchangeConfig.getBase(), currencyCode);
        HistoricalRates yesterdayRate = yesterdayRateResponse.getBody();
        HistoricalRates currentRate = currentRateResponse.getBody();

        if (yesterdayRate == null || currentRate == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (yesterdayRate.getRate().getCode() == null || currentRate.getRate().getCode() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        double differenceInRate = yesterdayRate.getRate().getValue() - currentRate.getRate().getValue();

        if (differenceInRate < 0) {

        }
        else {

        }
        return new ResponseEntity<>(differenceInRate, HttpStatus.OK);
    }
}
