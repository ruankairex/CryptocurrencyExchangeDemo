package com.ruan.coinDesk;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port=8080")
public class CallCoinDeskApiTest {

    @Autowired private  TestRestTemplate restTemplate;
    @Value("${coindesk.api.url}")
    private String API_URL ;


    @Test
    public void testGetCoindeskData() throws IOException {
        ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);
        assertEquals(200, response.getStatusCodeValue());
        System.out.println(response.getStatusCodeValue());
        System.out.println(response.getBody());
    }

}
