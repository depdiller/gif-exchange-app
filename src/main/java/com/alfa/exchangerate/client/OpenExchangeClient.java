package com.alfa.exchangerate.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "openExchangeClient",
        url = "${openexchangerate.api.url}")
public interface OpenExchangeClient {
    @RequestMapping(method = RequestMethod.GET,
            path = "/latest.json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getLatestRate(@RequestParam String app_id);
}
