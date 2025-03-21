package com.ruan.coinDesk;


import com.ruan.coinDesk.model.CoinDeskPOJO.CoindeskApiResponse;
import com.ruan.coinDesk.service.service.CoinDeskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DataTransferTest {

    @Autowired
    private CoinDeskService coinDeskService;

    private ResponseEntity<String> apiResponse;
    private final String json = "{\n" +
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

    @BeforeEach
    public void init(){
        apiResponse = new ResponseEntity<>(json, HttpStatus.OK);
    }

    @Test
    public void testDataTransfer(){
        CoindeskApiResponse coindeskApiResponse = coinDeskService.transResponseToDto(apiResponse);

        // 確認 time 物件不為空
        assertNotNull(coindeskApiResponse.getTime());
        assertEquals("Sep 2, 2024 07:07:20 UTC", coindeskApiResponse.getTime().getUpdated());
        assertEquals("2024-09-02T07:07:20+00:00", coindeskApiResponse.getTime().getUpdatedISO());
        assertEquals("Sep 2, 2024 at 08:07 BST", coindeskApiResponse.getTime().getUpdatedUK());

        // 確認 disclaimer 與 chartName 正確
        assertEquals("just for test", coindeskApiResponse.getDisclaimer());
        assertEquals("Bitcoin", coindeskApiResponse.getChartName());

        // 確認 bpi 物件不為空並且包含資料
        // 確認USD
        assertNotNull(coindeskApiResponse.getBpi());
        assertNotNull(coindeskApiResponse.getBpi().get("USD"));
        assertEquals("USD", coindeskApiResponse.getBpi().get("USD").getCode());
        assertEquals("&#36;", coindeskApiResponse.getBpi().get("USD").getSymbol());
        assertEquals("57,756.298", coindeskApiResponse.getBpi().get("USD").getRate());
        assertEquals("United States Dollar", coindeskApiResponse.getBpi().get("USD").getDescription());
        assertEquals(57756.2984, coindeskApiResponse.getBpi().get("USD").getRateFloat(), 0.001);

        // 確認GBP
        assertNotNull(coindeskApiResponse.getBpi().get("GBP"));
        assertEquals("GBP", coindeskApiResponse.getBpi().get("GBP").getCode());
        assertEquals("&pound;", coindeskApiResponse.getBpi().get("GBP").getSymbol());
        assertEquals("43,984.02", coindeskApiResponse.getBpi().get("GBP").getRate());
        assertEquals("British Pound Sterling", coindeskApiResponse.getBpi().get("GBP").getDescription());
        assertEquals(43984.0203, coindeskApiResponse.getBpi().get("GBP").getRateFloat(), 0.001);

        // 確認EUR
        assertNotNull(coindeskApiResponse.getBpi().get("EUR"));
        assertEquals("EUR", coindeskApiResponse.getBpi().get("EUR").getCode());
        assertEquals("&euro;", coindeskApiResponse.getBpi().get("EUR").getSymbol());
        assertEquals("52,243.287", coindeskApiResponse.getBpi().get("EUR").getRate());
        assertEquals("Euro", coindeskApiResponse.getBpi().get("EUR").getDescription());
        assertEquals(52243.2865, coindeskApiResponse.getBpi().get("EUR").getRateFloat(), 0.001);
    }


}
