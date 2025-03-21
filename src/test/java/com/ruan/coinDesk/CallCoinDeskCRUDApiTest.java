package com.ruan.coinDesk;



import com.ruan.coinDesk.controller.CoinDeskController;
import com.ruan.coinDesk.dao.CryptoCurrencyExchangeRateRepository;
import com.ruan.coinDesk.model.CoinDeskPOJO.CryptoCurrencyExchangeRate;
import com.ruan.coinDesk.model.CoinDeskPOJO.CryptocurrencyExchangeRateRequest;
import com.ruan.coinDesk.service.CoinDeskFlow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CallCoinDeskCRUDApiTest {

    @Autowired
    CryptoCurrencyExchangeRateRepository cryptoCurrencyExchangeRateRepository;
    @Autowired
    CoinDeskController coinDeskController;

    // 測試資料
    private CryptocurrencyExchangeRateRequest request;

    @BeforeEach
    public void setup() throws Exception {
        CryptoCurrencyExchangeRate request = new CryptoCurrencyExchangeRate();
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
    
    // Test GET 
    @Test
    public void CallApiGetDataTest() throws Exception {
        ResponseEntity<String> coinDesk = coinDeskController.getCoinDesk("Bitcoin", "USD");
        assertEquals(200, coinDesk.getStatusCodeValue());
        String body = coinDesk.getBody();
        System.out.println(body);
    }

    // 測試 POST /coindesk
    @Test
    public void CallApiPostDataTest() throws Exception {
        CryptocurrencyExchangeRateRequest request = new CryptocurrencyExchangeRateRequest();
        request.setChartName("Bitcoin");
        request.setCurrencyCode("GBP");
        request.setRate("43,984.02");
        request.setDescription("British Pound Sterling");
        request.setSymbol("&pound;");
        request.setRateFloat(new BigDecimal("43984.0203"));

        ResponseEntity<String> coinDesk = coinDeskController.insertCoinDesk(request);
        assertEquals(200, coinDesk.getStatusCodeValue());

        List<CryptoCurrencyExchangeRate> byChartNameAndCurrencyCode = cryptoCurrencyExchangeRateRepository.findByChartNameAndCurrencyCode("Bitcoin", "GBP");
        assertEquals("英鎊", byChartNameAndCurrencyCode.get(0).getCurrencyChineseName());
    }


    @Test
    public void CallApiUpdateDataTest() throws Exception {
        CryptocurrencyExchangeRateRequest request = new CryptocurrencyExchangeRateRequest();
        request.setChartName("Bitcoin");
        request.setCurrencyCode("USD");
        request.setRate("66,666.666");
        request.setDescription("United States Dollar");
        request.setSymbol("&#36;");
        request.setRateFloat(new BigDecimal("66666.666"));

        // validate status code
        ResponseEntity<String> coinDesk = coinDeskController.updateCoinDesk(request);
        assertEquals(200, coinDesk.getStatusCodeValue());

        CryptoCurrencyExchangeRate queryResult = cryptoCurrencyExchangeRateRepository
                .findByChartNameAndCurrencyCode("Bitcoin", "USD").get(0);
        // validate lastUpdated
        String lastUpdatedString = queryResult.getLastUpdated();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate lastUpdated = LocalDate.parse(lastUpdatedString, formatter);
        LocalDate today = LocalDate.now();
        assertEquals(today, lastUpdated);
        // validate updated info
        assertEquals("66,666.666", queryResult.getRate());
        assertEquals(new BigDecimal("66666.666").setScale(4, RoundingMode.HALF_UP), queryResult.getRateFloat());
    }


    @Test
    public void CallApiDeleteDataTest() throws Exception {
        CryptocurrencyExchangeRateRequest request = new CryptocurrencyExchangeRateRequest();
        request.setChartName("Bitcoin");
        request.setCurrencyCode("USD");
        // validate status code
        ResponseEntity<String> coinDesk = coinDeskController.deleteCoinDesk(request);
        assertEquals(200, coinDesk.getStatusCodeValue());

        List<CryptoCurrencyExchangeRate> byChartNameAndCurrencyCode = cryptoCurrencyExchangeRateRepository.findByChartNameAndCurrencyCode("Bitcoin", "USD");
        assertEquals(0, byChartNameAndCurrencyCode.size());
    }
   

}
