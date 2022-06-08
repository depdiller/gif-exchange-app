package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.client.GiphyClient;
import com.alfa.exchangerate.client.OpenExchangeClient;
import com.alfa.exchangerate.config.GiphyConfig;
import com.alfa.exchangerate.config.OpenExchangeConfig;
import com.alfa.exchangerate.model.HistoricalRates;
import com.alfa.exchangerate.service.GiphyService;
import com.alfa.exchangerate.service.OpenExchangeRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api")
@Validated
public class AppController {
    private final OpenExchangeRateService openExchangeRateService;
    private final GiphyService giphyService;
    private final OpenExchangeClient openExchangeClient;
    private final OpenExchangeConfig openExchangeConfig;
    private final Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public AppController(OpenExchangeRateService openExchangeRateService, OpenExchangeClient openExchangeClient,
                         GiphyClient giphyClient,
                         OpenExchangeConfig exchangeConfig,
                         GiphyConfig giphyConfig, GiphyService giphyService) {
        this.openExchangeRateService = openExchangeRateService;
        this.openExchangeClient = openExchangeClient;
        openExchangeConfig = exchangeConfig;
        this.giphyService = giphyService;
    }

    @GetMapping ("/latest")
    public String getRates(Model model){
        List<String> currencyCodes = openExchangeRateService.getCurrencyCodes();
        model.addAttribute("currencyCodes", currencyCodes);
        return "home";
    }

//    @GetMapping("/gif/{tag}")
//    @ResponseBody
//    public ResponseEntity<Object> getRandomGif(@PathVariable String tag) {
//        giphyService.getCorrectUrl(tag);
//    }

    @GetMapping("/difference-in-rate")
    public String checkDifferenceInRate(Model model, @RequestParam @Size(max = 3, min = 3) String currencyCode) {
        String currentDate = dateFormat.format(new Date());
        calendar.add(Calendar.DATE, -1);
        String yesterday = dateFormat.format(calendar.getTime());

        ResponseEntity<HistoricalRates> yesterdayRateResponse = openExchangeClient
                .getRateByDate(currentDate, openExchangeConfig.getAppId(), openExchangeConfig.getBase(), currencyCode);
        ResponseEntity<HistoricalRates> currentRateResponse = openExchangeClient
                .getRateByDate(yesterday, openExchangeConfig.getAppId(), openExchangeConfig.getBase(), currencyCode);
        HistoricalRates yesterdayRate = yesterdayRateResponse.getBody();
        HistoricalRates currentRate = currentRateResponse.getBody();

//        if (yesterdayRate == null || currentRate == null)
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        if (yesterdayRate.getRate().getCode() == null || currentRate.getRate().getCode() == null)
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        double differenceInRate = yesterdayRate.getRate().getValue() - currentRate.getRate().getValue();

        String url;
        String tag = differenceInRate < 0 ? "broke" : "rich";
        url = giphyService.getCorrectUrl(tag);
        model.addAttribute("tag", tag);
        model.addAttribute("url", url);
        return "result-gif";
    }
}
