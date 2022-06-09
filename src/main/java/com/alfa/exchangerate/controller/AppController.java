package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.model.GifSearchTag;
import com.alfa.exchangerate.service.GiphyService;
import com.alfa.exchangerate.service.OpenExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@Controller
@Validated
public class AppController {
    private final OpenExchangeRateService openExchangeRateService;
    private final GiphyService giphyService;

    public AppController(OpenExchangeRateService openExchangeRateService,
                         GiphyService giphyService) {
        this.openExchangeRateService = openExchangeRateService;
        this.giphyService = giphyService;
    }

    @GetMapping ("/home")
    public String getRates(Model model){
        List<String> currencyCodes = openExchangeRateService.getCurrencyCodes();
        model.addAttribute("currencyCodes", currencyCodes);
        return "home";
    }

    @GetMapping("/difference-in-rate")
    public String checkDifferenceInRate(Model model, @RequestParam @Size(max = 3, min = 3) String currencyCode) {
        if (currencyCode.equals("USD")) {
            String message = "Default base is USD. Try another currency";
            log.debug(message);
            model.addAttribute("message", message);
            return "error";
        }
        Double differenceInRate = openExchangeRateService.getDifferenceInRateTodayYesterday(currencyCode);
        if (differenceInRate == null) {
            log.debug("getDifferenceInRateTodayYesterday error");
            return "error";
        }
        String url;
        String tag = differenceInRate < 0 ? GifSearchTag.Broke.toString() : GifSearchTag.Rich.toString();
        url = giphyService.getCorrectUrl(tag);
        if (url == null) {
            log.debug("Giphy Service error. Check tag: " + tag);
            return "error";
        }
        tag = "We are " + tag + "!";
        model.addAttribute("tag", tag);
        model.addAttribute("url", url);
        return "result-gif";
    }
}
