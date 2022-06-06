package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.client.OpenExchangeClient;
import com.alfa.exchangerate.config.OpenExchangeConfig;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/latest")
public class ExchangeRateController {
    private final OpenExchangeClient openExchangeClient;
    private final OpenExchangeConfig openExchangeConfig;

    public ExchangeRateController(OpenExchangeClient client,
                           OpenExchangeConfig config) {
        openExchangeClient = client;
        openExchangeConfig = config;
    }

    @GetMapping
    public ResponseEntity<Object> getLatestRate() {
        try {;
            return new ResponseEntity<>(openExchangeClient.getLatestRate(openExchangeConfig.getAppId()),
                    HttpStatus.OK);
        }
        catch (FeignException feignEx) {
            return new ResponseEntity<>(feignEx.contentUTF8(),
                    HttpStatus.valueOf(feignEx.status()));
        }
    }
}
