package com.alfa.exchangerate.service;

import com.alfa.exchangerate.client.OpenExchangeClient;
import com.alfa.exchangerate.model.HistoricalRates;
import com.alfa.exchangerate.model.LatestResult;
import com.alfa.exchangerate.model.Rate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@SpringBootTest
class OpenExchangeRateServiceTest {
    @Autowired
    private OpenExchangeRateService openExchangeRateService;
    @MockBean
    private OpenExchangeClient openExchangeClient;
    @Value("${openexchangerate.api.base}")
    private String base;
    @Value("${openexchangerate.api.appId}")
    private String appId;
    private LatestResult latestResult;
    private HistoricalRates historicalRatesToday;
    private HistoricalRates historicalRatesYesterday;
    private final Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Double rate1 = 13.22;
    private final Double rate2 = 11.0312;

    @BeforeEach
    public void init() {
        Map<String, Double> rates = Map.of(
                "USD", 14.23,
                "RUB", 23.11
        );
        Rate rateToday = new Rate("RUB", rate1);
        Rate rateYesterday = new Rate("RUB", rate2);
        latestResult = new LatestResult("1", base, rates);
        historicalRatesToday = new HistoricalRates("1", base, rateToday);
        historicalRatesYesterday = new HistoricalRates("2", base, rateYesterday);
    }

    @Test
    void getCurrencyCodes() {
        Mockito.when(openExchangeClient.getLatestRate(eq(appId), eq(base)))
                .thenReturn(new ResponseEntity<>(latestResult, HttpStatus.OK));
        List<String> currencyCodes = openExchangeRateService.getCurrencyCodes();
        List<String> testList = Arrays.asList("RUB", "USD");
        assertTrue(testList.size() == currencyCodes.size()
                && testList.containsAll(currencyCodes) && currencyCodes.containsAll(testList));
    }

    @Test
    void getDifferenceInRateTodayYesterday() {
        String currentDate = dateFormat.format(new Date());
        calendar.add(Calendar.DATE, -1);
        String yesterday = dateFormat.format(calendar.getTime());

        Mockito.when(openExchangeClient.getRateByDate(eq(currentDate), eq(appId), eq(base), anyString()))
                .thenReturn(new ResponseEntity<>(historicalRatesToday, HttpStatus.OK));
        Mockito.when(openExchangeClient.getRateByDate(eq(yesterday), eq(appId), eq(base), anyString()))
                .thenReturn(new ResponseEntity<>(historicalRatesYesterday, HttpStatus.OK));
        Double difference = openExchangeRateService.getDifferenceInRateTodayYesterday("RUB");
        assertEquals(rate1 - rate2, difference, 0.01);
    }
}