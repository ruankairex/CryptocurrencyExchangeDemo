package com.ruan.coinDesk;

import com.ruan.coinDesk.service.flow.CoinDeskFlow;
import com.ruan.coinDesk.service.service.CallApiService;
import com.ruan.coinDesk.service.service.CoinDeskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CallCoinDeskDataTransferTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CallApiService callApiService;

    @Autowired
    private CoinDeskFlow coinDeskFlow;

    @Autowired
    private CoinDeskService coinDeskService;

    private final String apiResponse = "{\n" +
            "\"time\": {\n" +
            "\"updated\": \"Sep 2, 2024 07:07:20 UTC\",\n" +
            "\"updatedISO\": \"2024-09-02T07:07:20+00:00\",\n" +
            "\"updateduk\": \"Sep 2, 2024 at 08:07 BST\"\n" +
            "},\n" +
            "\"disclaimer\": \"just for test\",\n" +
            "\"chartName\": \"Bitcoin\",\n" +
            "\"bpi\": {\n" +
            "\"USD\": {\n" +
            "\"code\": \"USD\",\n" +
            "\"symbol\": \"&#36;\",\n" +
            "\"rate\": \"57,756.298\",\n" +
            "\"description\": \"United States Dollar\",\n" +
            "\"rate_float\": 57756.2984\n" +
            "},\n" +
            "\"GBP\": {\n" +
            "\"code\": \"GBP\",\n" +
            "\"symbol\": \"&pound;\",\n" +
            "\"rate\": \"43,984.02\",\n" +
            "\"description\": \"British Pound Sterling\",\n" +
            "\"rate_float\": 43984.0203\n" +
            "},\n" +
            "\"EUR\": {\n" +
            "\"code\": \"EUR\",\n" +
            "\"symbol\": \"&euro;\",\n" +
            "\"rate\": \"52,243.287\",\n" +
            "\"description\": \"Euro\",\n" +
            "\"rate_float\": 52243.2865\n" +
            "}\n" +
            "}\n" +
            "}";

    private final String jsonResponse = "{"
            + "\"updatedTime\":\"2024/09/02 07:07:20\","
            + "\"updatedTimeISO\":\"2024/09/02 07:07:20\","
            + "\"updatedTimeUK\":\"2024/09/02 08:07:00\","
            + "\"cryptocurrency\":\"Bitcoin\","
            + "\"cryptocurrencyChineseName\":\"比特幣\","
            + "\"currency\":["
            + "{\"currencyCode\":\"USD\",\"currencyChineseName\":\"美元\",\"rate\":\"57756.2984\"},"
            + "{\"currencyCode\":\"GBP\",\"currencyChineseName\":\"英鎊\",\"rate\":\"43984.0203\"},"
            + "{\"currencyCode\":\"EUR\",\"currencyChineseName\":\"歐元\",\"rate\":\"52243.2865\"}"
            + "]"
            + "}";

    @Test
    void testGetCoinDesk_Success() throws Exception {
        // 模擬 callCoinDeskApi() 回傳結果
        ResponseEntity<String> mockResponse = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        Mockito.when(callApiService.callCoinDeskAPI()).thenReturn(mockResponse);

        // 使用 MockMvc 呼叫 API，驗證回應狀態碼 & JSON 結果
        mockMvc.perform(get("/getCoinDeskInfo"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }


}