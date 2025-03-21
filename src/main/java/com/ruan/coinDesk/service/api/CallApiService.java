package com.ruan.coinDesk.service.api;

import com.ruan.coinDesk.service.process.CoinDeskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CallApiService {
    private static final Logger log = LoggerFactory.getLogger(CoinDeskService.class);
    @Autowired
    RestTemplate restTemplate;
    @Value("${coindesk.api.url}")
    private String API_URL ;


    /**
     * Call CoinDeskAPI to get Response in String type
     *
     * @return
     */
    public ResponseEntity<String> callCoinDeskAPI() {
        ResponseEntity<String> response;
        try{
            response = restTemplate.getForEntity(API_URL, String.class);
        } catch (Exception e){
            log.error("api call CoinDesk error", e);
            throw new RuntimeException("System Error, please wait and try again");
        }
        if(response.getStatusCodeValue() == 200){
            return response;
        }else{
            log.error("api call CoinDesk error, HTTP Status: {}", response.getStatusCodeValue());
            throw new RuntimeException("Failed to call CoinDesk API, HTTP Status: " + response.getStatusCodeValue());
        }
    }
}
