package com.ruan.coinDesk.controller;

import com.ruan.coinDesk.exception.CoinDeskApiException;
import com.ruan.coinDesk.model.CoinDeskPOJO.CryptocurrencyExchangeRateRequest;
import com.ruan.coinDesk.service.flow.CoinDeskFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CoinDeskController {
    private static final Logger log = LoggerFactory.getLogger(CoinDeskController.class);
    @Autowired
    CoinDeskFlow coinDeskFlow;

    /**
     * Use this API to call CoinDesk API(outer) and process the response
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getCoinDeskInfo")
    public ResponseEntity<String> getCoinDesk() {
        try {
            String jsonResponse = coinDeskFlow.callCoinDeskApi();
            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        } catch (CoinDeskApiException e) {
            log.error("logic error, message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error calling CoinDesk API : " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error, message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected error occurred.");
        }
    }

    /**
     * Get coindesk info by chartName and currencyCode
     *
     * @param chartName
     * @param currencyCode
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/coindesk/{chartName}/{currencyCode}")
    public ResponseEntity<String> getCoinDesk(@PathVariable("chartName") String chartName, @PathVariable("currencyCode") String currencyCode) {
        try {
            String jsonResponse = coinDeskFlow.findByChartNameAndCurrencyCode(chartName, currencyCode);
            return ResponseEntity.ok(jsonResponse);
        } catch (CoinDeskApiException e) {
            log.error("logic error, message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error message : " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error, message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected error occurred.");
        }
    }

    /**
     *  Use this API to insert data(CRYPTO_CURRENCY_EXCHANGE_RATES table)
     *  if successful, return success
     *  if failed, return error message
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/coindesk")
    public ResponseEntity<String> insertCoinDesk(@RequestBody CryptocurrencyExchangeRateRequest request) {
        try {
            coinDeskFlow.insertData(request);
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        } catch (CoinDeskApiException e) {
            log.error("logic error, message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error message : " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error, message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected error occurred.");
        }
    }

    /**
     *  Use this API to update data(CRYPTO_CURRENCY_EXCHANGE_RATES table)
     *  if successful, return success
     *  if failed, return error message
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/coindesk")
    public ResponseEntity<String> updateCoinDesk(@RequestBody CryptocurrencyExchangeRateRequest request) {
        try {
            coinDeskFlow.updateData(request);
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        } catch (CoinDeskApiException e) {
            log.error("logic error, message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error message : " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error, message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected error occurred.");
        }
    }


    /**
     *  Use this API to delete data(CRYPTO_CURRENCY_EXCHANGE_RATES table)
     *  if successful, return success
     *  if failed, return error message
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/coindesk")
    public ResponseEntity<String> deleteCoinDesk(@RequestBody CryptocurrencyExchangeRateRequest request) {
        try {
            coinDeskFlow.deleteData(request);
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        } catch (CoinDeskApiException e) {
            log.error("logic error, message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error message : " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error, message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected error occurred.");
        }
    }
}
