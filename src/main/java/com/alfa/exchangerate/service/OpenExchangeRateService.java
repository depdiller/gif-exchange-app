package com.alfa.exchangerate.service;

import com.alfa.exchangerate.client.OpenExchangeClient;
import com.alfa.exchangerate.config.OpenExchangeConfig;
import com.alfa.exchangerate.model.HistoricalRates;
import com.alfa.exchangerate.model.LatestResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OpenExchangeRateService {
    private final OpenExchangeClient openExchangeClient;
    private final OpenExchangeConfig openExchangeConfig;
    private final Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public OpenExchangeRateService(OpenExchangeClient openExchangeClient,
                                   OpenExchangeConfig exchangeConfig) {
        this.openExchangeClient = openExchangeClient;
        openExchangeConfig = exchangeConfig;
    }

    public List<String> getCurrencyCodes() {
        List<String> result = null;
        ResponseEntity<LatestResult> response = openExchangeClient
                .getLatestRate(openExchangeConfig.getAppId(), openExchangeConfig.getBase());
        if (response.getBody() != null)
            result = new ArrayList<>(response.getBody().getRates().keySet());
        return result;
    }

    public Double getDifferenceInRateTodayYesterday(String currencyCode) {
        String currentDate = dateFormat.format(new Date());
        calendar.add(Calendar.DATE, -1);
        String yesterday = dateFormat.format(calendar.getTime());

        ResponseEntity<HistoricalRates> yesterdayRateResponse = openExchangeClient
                .getRateByDate(currentDate, openExchangeConfig.getAppId(), openExchangeConfig.getBase(), currencyCode);
        ResponseEntity<HistoricalRates> currentRateResponse = openExchangeClient
                .getRateByDate(yesterday, openExchangeConfig.getAppId(), openExchangeConfig.getBase(), currencyCode);
        HistoricalRates yesterdayRate = yesterdayRateResponse.getBody();
        HistoricalRates currentRate = currentRateResponse.getBody();

        Double differenceInRate = null;
        if (currentRate != null && yesterdayRate != null) {
            differenceInRate = yesterdayRate.getRate().getValue() - currentRate.getRate().getValue();
        }
        return differenceInRate;
    }
}
