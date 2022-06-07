package com.alfa.exchangerate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "openexchangerate.api")
public class OpenExchangeConfig {
    private String appId;
    private String url;
    private String base;
}
