package com.alfa.exchangerate.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "GiphyClient",
        url = "${giphy.api.randomGifUrl}")
public interface GiphyClient {
    @RequestMapping(method = RequestMethod.GET,
            path = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getGif(@RequestParam String api_key, @RequestParam String tag);
}
