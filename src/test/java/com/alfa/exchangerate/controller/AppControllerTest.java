package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.service.GiphyService;
import com.alfa.exchangerate.service.OpenExchangeRateService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AppControllerTest {
    @MockBean
    private GiphyService giphyService;
    @MockBean
    private OpenExchangeRateService openExchangeRateService;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getRates() throws Exception {
        this.mockMvc.perform(get("/home")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Exchange Rate app")));
    }

    @Test
    void checkDifferenceInRate() throws Exception {
        Mockito.when(giphyService.getCorrectUrl("rich")).thenReturn("url");
        this.mockMvc.perform(get("/difference-in-rate?currencyCode=RUB")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("We are")));
    }

    @Test
    void checkDifferenceInRateInvalidCode() throws Exception {
        this.mockMvc.perform(get("/difference-in-rate?currencyCode=RUBfda")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Something went wrong")));
    }

    @Test
    void checkDifferenceInRateInvalidGifUrl() throws Exception {
        Mockito.when(giphyService.getCorrectUrl("")).thenReturn(null);
        this.mockMvc.perform(get("/difference-in-rate?currencyCode=RUB")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Something went wrong")));
    }

    @Test
    void checkDifferenceInRateInvalidDifferenceInRate() throws Exception {
        Mockito.when(openExchangeRateService.getDifferenceInRateTodayYesterday("")).thenReturn(null);
        this.mockMvc.perform(get("/difference-in-rate?currencyCode=RUB")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Something went wrong")));
    }
}