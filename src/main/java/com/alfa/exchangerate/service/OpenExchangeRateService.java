package com.alfa.exchangerate.service;

import com.alfa.exchangerate.client.OpenExchangeClient;
import com.alfa.exchangerate.config.OpenExchangeConfig;
import com.alfa.exchangerate.model.LatestResult;
import com.alfa.exchangerate.model.Rate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenExchangeRateService {
    private final OpenExchangeClient openExchangeClient;
    private final OpenExchangeConfig openExchangeConfig;

    public OpenExchangeRateService(OpenExchangeClient openExchangeClient,
                                   OpenExchangeConfig exchangeConfig) {
        this.openExchangeClient = openExchangeClient;
        openExchangeConfig = exchangeConfig;
    }

    public List<String> getCurrencyCodes() {
        List<String> result = null;
        ResponseEntity<LatestResult> response = openExchangeClient
                .getLatestRate(openExchangeConfig.getAppId(), openExchangeConfig.getBase());
        result = new ArrayList<String>(response.getBody().getRates().keySet());
        return result;
    }
}
