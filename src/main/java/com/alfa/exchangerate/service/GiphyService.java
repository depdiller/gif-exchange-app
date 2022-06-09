package com.alfa.exchangerate.service;

import com.alfa.exchangerate.client.GiphyClient;
import com.alfa.exchangerate.config.GiphyConfig;
import com.alfa.exchangerate.model.GiphyRes;
import org.springframework.stereotype.Service;

@Service
public class GiphyService {
    private final GiphyClient giphyClient;
    private final GiphyConfig giphyConfig;

    public GiphyService(GiphyClient giphyClient, GiphyConfig giphyConfig) {
        this.giphyClient = giphyClient;
        this.giphyConfig = giphyConfig;
    }

    public String getCorrectUrl(String tag) {
        GiphyRes giphyRes = giphyClient.getGif(giphyConfig.getApiKey(), tag).getBody();
        String url = null;
        if (giphyRes != null) {
            url = giphyRes.getData().getImages().get("downsized").getUrl();
        }
        return url;
    }
}
