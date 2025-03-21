package com.ruan.coinDesk.service;


import com.ruan.coinDesk.exception.CoinDeskApiException;
import com.ruan.coinDesk.model.CoinDeskPOJO.CoinDeskDto;
import com.ruan.coinDesk.model.CoinDeskPOJO.CoindeskApiResponse;
import com.ruan.coinDesk.model.CoinDeskPOJO.CryptoCurrencyExchangeRate;
import com.ruan.coinDesk.model.CoinDeskPOJO.CryptocurrencyExchangeRateRequest;
import com.ruan.coinDesk.service.api.CallApiService;
import com.ruan.coinDesk.service.check.CoinDeskServiceCheck;
import com.ruan.coinDesk.service.process.CoinDeskService;
import com.ruan.coinDesk.util.CurrencyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This is the main process service of the Coindesk controller.
 */
@Service
public class CoinDeskFlow {
    @Autowired
    private CoinDeskService coinDeskService;
    @Autowired private CallApiService callApiService;
    @Autowired private CoinDeskServiceCheck coinDeskServiceCheck;


    public String callCoinDeskApi() {
        // call api
        ResponseEntity<String> apiResponse = callApiService.callCoinDeskAPI();
        // convert to dto
        CoindeskApiResponse coinDeskResponse = coinDeskService.transResponseToDto(apiResponse);
        // process data
        CoinDeskDto coinDeskDto = coinDeskService.formatData(coinDeskResponse);
        // 序列化
        String json = coinDeskService.convertToJson(coinDeskDto);

        return json;
    }


    /**
     * Insert data , before insert there is a data check for is empty and is legal
     *
     * @param data
     * @throws Exception
     */
    @Transactional
    public void insertData(CryptocurrencyExchangeRateRequest data) throws Exception {
        // check data is exist
        boolean isExist = coinDeskService.checkDataIsExist(data);
        if (isExist) {
            throw new CoinDeskApiException("Data already exists: " + data.getChartName() + "," + data.getCurrencyCode());
        }
        // check data is legal
        List<String> errorList = coinDeskServiceCheck.checkCoinDeskInfo(data);
        if (!errorList.isEmpty()){
            throw new CoinDeskApiException(String.join(", ", errorList));
        }
        // process and insert data
        String chartName = data.getChartName();
        if (CurrencyMap.containsCryptoCurrency(chartName)) {
            coinDeskService.insertData(data);
        } else {
            throw new CoinDeskApiException("Unsupported cryptocurrency: " + chartName);
        }
    }


    /**
     * Find data by chartName and currencyCode
     *
     * @param chartName
     * @param currencyCode
     * @return
     * @throws Exception
     */
    public String findByChartNameAndCurrencyCode(String chartName, String currencyCode) throws Exception {
        CryptoCurrencyExchangeRate queryResult = coinDeskService.findUniqueByChartNameAndCurrencyCode(chartName, currencyCode);
        try {
            return coinDeskService.convertToJson(queryResult);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * Update data By chartName and currencyCode
     *
     * @param request
     */
    @Transactional
    public void updateData(CryptocurrencyExchangeRateRequest request) {
        // find data by chartName and currencyCode and check data is exist
        CryptoCurrencyExchangeRate queryResult;
        try{
            queryResult = coinDeskService.findUniqueByChartNameAndCurrencyCode(request.getChartName(), request.getCurrencyCode());
        } catch (Exception e){
            throw new CoinDeskApiException("Data update failed , data not exist : " + request.getChartName() + "," + request.getCurrencyCode());
        }
        // update data
        coinDeskService.updateData(queryResult , request);
    }


    /**
     * Delete data By chartName and currencyCode
     *
     * @param request
     */
    @Transactional
    public void deleteData(CryptocurrencyExchangeRateRequest request) {
        // find data by chartName and currencyCode and check data is exist
        CryptoCurrencyExchangeRate queryResult;
        try {
            queryResult = coinDeskService.findUniqueByChartNameAndCurrencyCode(request.getChartName(), request.getCurrencyCode());
        } catch (Exception e){
            throw new CoinDeskApiException("Data delete failed , data not exist : " + request.getChartName() + "," + request.getCurrencyCode());
        }
        // update data
        coinDeskService.deleteDataById(queryResult.getCurrencyId());
    }
}
