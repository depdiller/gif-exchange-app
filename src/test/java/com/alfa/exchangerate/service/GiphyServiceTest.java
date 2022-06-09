package com.alfa.exchangerate.service;

import com.alfa.exchangerate.client.GiphyClient;
import com.alfa.exchangerate.model.GiphyData;
import com.alfa.exchangerate.model.GiphyRes;
import com.alfa.exchangerate.model.Image;
import org.junit.jupiter.api.Assertions;
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

import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
class GiphyServiceTest {
    @Autowired
    private GiphyService giphyService;
    @MockBean
    private GiphyClient giphyClient;
    @Value("${giphy.api.apiKey}")
    private String appKey;
    private GiphyRes giphyRes;
    private final String correctUrl = "test/url";

    @BeforeEach
    public void init() {
        Map<String, Image> images = Map.of(
                "downsized", new Image("1", "2", correctUrl, "3"),
                "another", new Image("1", "2", "wrong/url", "3")
        );
        GiphyData data = new GiphyData(images);
        giphyRes = new GiphyRes(data);
    }

    @Test
    void getCorrectUrl() {
        Mockito.when(giphyClient.getGif(eq(appKey), anyString()))
                .thenReturn(new ResponseEntity<>(giphyRes, HttpStatus.OK));
        String url = giphyService.getCorrectUrl("rich");
        Assertions.assertEquals(correctUrl, url);
    }
}