package com.ruan.coinDesk;


import com.ruan.coinDesk.dao.CryptoCurrencyExchangeRateRepository;
import com.ruan.coinDesk.model.CoinDeskPOJO.CryptoCurrencyExchangeRatePO;
import com.ruan.coinDesk.service.service.CoinDeskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class CallCoinDeskCRUDApiTest {

    @Autowired
    CryptoCurrencyExchangeRateRepository cryptoCurrencyExchangeRateRepository;
    @Autowired
    CoinDeskService coinDeskService;
    @Autowired
    private MockMvc mockMvc;

    private final String insertData = "{\n" +
            "  \"chartName\":\"Bitcoin\",\n" +
            "  \"currencyCode\": \"GBP\",\n" +
            "  \"rate\": \"43,984.02\",\n" +
            "  \"description\": \"British Pound Sterling\",\n" +
            "  \"symbol\": \"&pound;\",\n" +
            "  \"rate_float\": 43984.0203\n" +
            "}";

    private final String updateData = "{\n" +
            "  \"chartName\":\"Bitcoin\",\n" +
            "  \"currencyCode\": \"USD\",\n" +
            "  \"currencyChineseName\":\"測試123\",\n" +
            "  \"rate\": \"66,666.298\",\n" +
            "  \"description\": \"United States Dollar\",\n" +
            "  \"symbol\": \"$\",\n" +
            "  \"rate_float\": 66666.2982\n" +
            "}";

    private final String deleteData = "{\n" +
            "  \"chartName\":\"Bitcoin\",\n" +
            "  \"currencyCode\": \"USD\"\n" +
            "}";

    @BeforeEach
    public void setup() throws Exception {
        CryptoCurrencyExchangeRatePO request = new CryptoCurrencyExchangeRatePO();
        request.setChartName("Bitcoin");
        request.setCurrencyCode("USD");
        request.setCurrencyChineseName("美元");
        request.setRate("57,756.298");
        request.setDescription("United States Dollar");
        request.setCurrencySymbol("&#36;");
        request.setRateFloat(new BigDecimal("57756.2984"));
        request.setLastUpdated("2025-03-21 22:54:46");

        cryptoCurrencyExchangeRateRepository.save(request);
    }
    @AfterEach
    public void afterEach() throws Exception {
        cryptoCurrencyExchangeRateRepository.deleteAll();
    }

    @Test
    public void testSelectData() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/coindesk/Bitcoin/USD"))
                .andExpect(status().isOk());
        resultActions.andDo(print());
    }

    @Test
    public void testInsertData() throws Exception {
        mockMvc.perform(post("/coindesk")
                        .contentType(MediaType.APPLICATION_JSON)  // 設定 Content-Type
                        .content(insertData))  // 設定 request body
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testUpdateData() throws Exception {
        mockMvc.perform(put("/coindesk")
                        .contentType(MediaType.APPLICATION_JSON)  // 設定 Content-Type
                        .content(updateData))  // 設定 request body
                .andExpect(status().isOk())
                .andDo(print());
        CryptoCurrencyExchangeRatePO uniqueByChartNameAndCurrencyCode =
                coinDeskService.findUniqueByChartNameAndCurrencyCode("Bitcoin", "USD");
        assertEquals("測試123", uniqueByChartNameAndCurrencyCode.getCurrencyChineseName());
        assertEquals("66,666.298", uniqueByChartNameAndCurrencyCode.getRate());
    }

    @Test
    public void testDeleteData() throws Exception {
        mockMvc.perform(delete("/coindesk")
                        .contentType(MediaType.APPLICATION_JSON)  // 設定 Content-Type
                        .content(deleteData))
                .andExpect(status().isOk())
                .andDo(print());

        assertThrows(Exception.class, () -> {
            coinDeskService.findUniqueByChartNameAndCurrencyCode("Bitcoin", "USD");
        });
    }
}
