package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.client.OpenExchangeClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/latest")
public class ExchangeRateController {
    private final OpenExchangeClient openExchangeClient;

    @GetMapping
    public ResponseEntity getLatestRate() {
        try {
            return openExchangeClient.getLatestRate("${openexchangerates.api.app_id}");
        }
        catch (FeignException feignEx) {
            return new ResponseEntity(feignEx.contentUTF8(), HttpStatus.valueOf(feignEx.status()));
        }
    }
}
