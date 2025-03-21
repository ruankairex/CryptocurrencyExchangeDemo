package com.ruan.coinDesk;

import com.ruan.coinDesk.controller.CoinDeskController;
import com.ruan.coinDesk.service.CoinDeskFlow;
import com.ruan.coinDesk.service.process.CoinDeskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoinDeskController.class)  // 這只會加載 Controller 層
public class CoinDeskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoinDeskFlow coinDeskFlow;
    @MockBean
    private CoinDeskService coinDeskService;

    private final String jsonResponse = "{"
            + "\"updatedTime\":\"2024/09/02 07:07:20\","
            + "\"updatedTimeISO\":\"2024/09/02 07:07:20\","
            + "\"updatedTimeUK\":\"2024/09/02 08:07:00\","
            + "\"cryptocurrency\":\"Bitcoin\","
            + "\"cryptocurrencyChineseName\":\"比特幣\","
            + "\"currency\":["
            + "    {\"currencyCode\":\"USD\", \"currencyChineseName\":\"美元\", \"rate\":\"57756.2984\"},"
            + "    {\"currencyCode\":\"GBP\", \"currencyChineseName\":\"英鎊\", \"rate\":\"43984.0203\"},"
            + "    {\"currencyCode\":\"EUR\", \"currencyChineseName\":\"歐元\", \"rate\":\"52243.2865\"}"
            + "]"
            + "}";

    @Test
    public void testGetCoinDesk_Success() throws Exception {

        when(coinDeskFlow.callCoinDeskApi()).thenReturn(jsonResponse);  // 模擬 callCoinDeskApi 方法的回應

        mockMvc.perform(get("/getCoinDeskInfo"))
                .andExpect(status().isOk())  // 預期 HTTP 狀態是 200 OK
                .andExpect(content().json(jsonResponse));  // 檢查回應內容是否與預期相符
    }
}
