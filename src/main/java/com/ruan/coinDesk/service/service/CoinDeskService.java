package com.ruan.coinDesk.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruan.coinDesk.dao.CryptoCurrencyExchangeRateRepository;
import com.ruan.coinDesk.model.CoinDeskPOJO.CoinDeskDto;
import com.ruan.coinDesk.model.CoinDeskPOJO.CoindeskApiResponse;
import com.ruan.coinDesk.model.CoinDeskPOJO.CryptoCurrencyExchangeRate;
import com.ruan.coinDesk.model.CoinDeskPOJO.CryptocurrencyExchangeRateRequest;
import com.ruan.coinDesk.service.check.CoinDeskServiceCheck;
import com.ruan.coinDesk.util.CurrencyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CoinDeskService {
    private static final Logger log = LoggerFactory.getLogger(CoinDeskService.class);
    @Autowired ObjectMapper objectMapper;
    @Autowired CryptoCurrencyExchangeRateRepository cryptoCurrencyExchangeRateRepository;
    @Autowired CoinDeskServiceCheck coinDeskServiceCheck;

    public CryptoCurrencyExchangeRate findUniqueByChartNameAndCurrencyCode(String chartName, String currencyCode) throws Exception {
        List<CryptoCurrencyExchangeRate> byChartNameAndCurrencyCode = cryptoCurrencyExchangeRateRepository.findByChartNameAndCurrencyCode(chartName, currencyCode);
        if (byChartNameAndCurrencyCode != null && byChartNameAndCurrencyCode.size() == 1) {
            return byChartNameAndCurrencyCode.get(0);
        }
        log.error("query failed ." + chartName + " , " + currencyCode);
        throw new Exception();
    }

    public CoindeskApiResponse transResponseToDto(ResponseEntity<String> response)  {
        try{
            return objectMapper.readValue(response.getBody(), CoindeskApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Inserts a new CurrencyPO object into repository.
     * Creates a new CurrencyPO instance, sets the fields with values
     * rom the provided CurrencyPO object, and then saves it to the repository.
     *
     * @param cryptocurrency
     * @throws Exception
     */
    public void insertData(CryptocurrencyExchangeRateRequest cryptocurrency) throws Exception {

        String chineseName = CurrencyMap.getCurrencyName(cryptocurrency.getCurrencyCode());

        CryptoCurrencyExchangeRate bitcoinExchangeRate = new CryptoCurrencyExchangeRate();
        bitcoinExchangeRate.setChartName(cryptocurrency.getChartName());
        bitcoinExchangeRate.setCurrencyCode(cryptocurrency.getCurrencyCode());
        bitcoinExchangeRate.setCurrencyChineseName(chineseName);
        bitcoinExchangeRate.setRate(cryptocurrency.getRate());
        bitcoinExchangeRate.setRateFloat(cryptocurrency.getRateFloat());
        bitcoinExchangeRate.setDescription(cryptocurrency.getDescription());
        bitcoinExchangeRate.setCurrencySymbol(cryptocurrency.getSymbol());
        bitcoinExchangeRate.setLastUpdated(this.currentDateTime());

        try {
            cryptoCurrencyExchangeRateRepository.save(bitcoinExchangeRate);
        } catch (Exception e) {
            log.error("insert failed, message: {}", e.getMessage());
            throw new Exception("insert failed");
        }
    }

    /**
     * Check data is exist , if not or more than one then throw exception
     *
     * @param data
     * @return
     */

    public boolean checkDataIsExist(CryptocurrencyExchangeRateRequest data) {
        List<CryptoCurrencyExchangeRate> byChartNameAndCurrencyCode = cryptoCurrencyExchangeRateRepository.findByChartNameAndCurrencyCode(data.getChartName(), data.getCurrencyCode());
        return byChartNameAndCurrencyCode != null && byChartNameAndCurrencyCode.size() == 1;
    }

    /**
     * Convert object to Json
     *
     * @param object
     * @return
     */
    public String convertToJson(Object object) {
        if (object == null) {
            log.error("Object cannot be null");
            throw new IllegalArgumentException("Object cannot be null");
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("convertToJson failed , message: {}", e.getMessage());
            throw new RuntimeException("getting data failed");
        }
    }

    /**
     * Get current date time
     *
     * @return
     */
    private String currentDateTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(now);
    }


    public void updateData(CryptoCurrencyExchangeRate queryResult, CryptocurrencyExchangeRateRequest request) {

        if (request.getCurrencyChineseName() != null) {
            queryResult.setCurrencyChineseName(request.getCurrencyChineseName());
        }
        if (request.getRate() != null) {
            queryResult.setRate(request.getRate());
        }
        if (request.getRateFloat() != null) {
            queryResult.setRateFloat(request.getRateFloat());
        }
        if (request.getDescription() != null) {
            queryResult.setDescription(request.getDescription());
        }
        if (request.getSymbol() != null) {
            queryResult.setCurrencySymbol(request.getSymbol());
        }
        queryResult.setLastUpdated(currentDateTime()); // 更新最後修改時間

        cryptoCurrencyExchangeRateRepository.save(queryResult);
    }

    public void deleteDataById(Long currencyId) {
        cryptoCurrencyExchangeRateRepository.deleteById(currencyId);
    }

    public CoinDeskDto formatData(CoindeskApiResponse coinDeskResponse) {
        CoinDeskDto coinDeskDto = new CoinDeskDto();
        String chartName = coinDeskResponse.getChartName();
        CoindeskApiResponse.TimeInfo rawTime = coinDeskResponse.getTime();
        this.setTimeInfo(coinDeskDto , rawTime);

        List<CoinDeskDto.CurrencyRateInfo> currencyList = new ArrayList<>();
        if (coinDeskResponse.getBpi().isEmpty() || !CurrencyMap.containsCryptoCurrency(chartName)) {
            return null;
        }
        for (Map.Entry<String, CoindeskApiResponse.BpiInfo> entry : coinDeskResponse.getBpi().entrySet()) {
            if (!CurrencyMap.containsCurrency(entry.getKey())) {
                continue;
            }
            CoindeskApiResponse.BpiInfo bpiInfo = entry.getValue();
            CoinDeskDto.CurrencyRateInfo currencyRateInfo = new CoinDeskDto.CurrencyRateInfo();
            currencyRateInfo.setCurrencyCode(bpiInfo.getCode());
            currencyRateInfo.setCurrencyChineseName(CurrencyMap.getCurrencyName(bpiInfo.getCode()));
            currencyRateInfo.setRate(String.valueOf(bpiInfo.getRateFloat()));

            currencyList.add(currencyRateInfo);
        }
        coinDeskDto.setCryptocurrency(chartName);
        coinDeskDto.setCryptocurrencyChineseName(CurrencyMap.getCryptoCurrencyName(chartName));
        coinDeskDto.setCurrency(currencyList);
        return coinDeskDto;
    }

    private void setTimeInfo(CoinDeskDto coinDeskDto, CoindeskApiResponse.TimeInfo rawTime) {
        String updated = rawTime.getUpdated();
        String updatedISO = rawTime.getUpdatedISO();
        String updatedUK = rawTime.getUpdatedUK();

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);
            ZonedDateTime updatedZDT = ZonedDateTime.parse(updated, inputFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            coinDeskDto.setUpdatedTime(updatedZDT.format(outputFormatter));
        } catch (Exception e) {
            log.error("setTimeInfo failed , message: {}", e.getMessage());
            coinDeskDto.setUpdatedTime("NULL");
        }

        try {
            ZonedDateTime updatedIsoZDT = ZonedDateTime.parse(updatedISO);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            coinDeskDto.setUpdatedTimeISO(updatedIsoZDT.format(outputFormatter));
        } catch (Exception e) {
            log.error("setIsoTimeInfo failed , message: {}", e.getMessage());
            coinDeskDto.setUpdatedTimeISO("NULL");
        }

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy 'at' HH:mm z", Locale.ENGLISH);
            ZonedDateTime updatedUkZDT = ZonedDateTime.parse(updatedUK, inputFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            coinDeskDto.setUpdatedTimeUK(updatedUkZDT.format(outputFormatter));
        } catch (Exception e) {
            log.error("setUkTimeInfo failed , message: {}", e.getMessage());
            coinDeskDto.setUpdatedTimeUK("NULL");
        }
    }


}
