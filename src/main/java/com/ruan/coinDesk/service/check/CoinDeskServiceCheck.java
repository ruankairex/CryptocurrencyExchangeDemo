package com.ruan.coinDesk.service.check;


import com.ruan.coinDesk.exception.CoinDeskApiException;
import com.ruan.coinDesk.model.CoinDeskPOJO.CoindeskApiResponse;
import com.ruan.coinDesk.model.CoinDeskPOJO.CryptocurrencyExchangeRateRequest;
import com.ruan.coinDesk.util.CurrencyMap;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CoinDeskServiceCheck {

    public List<String> checkCoinDeskInfo(CryptocurrencyExchangeRateRequest currencyVO) {
        List<String> errorList = new ArrayList<>();

        // 非空檢查
        if (currencyVO.getChartName() == null || currencyVO.getChartName().trim().isEmpty()) {
            errorList.add("ChartName cannot be null or empty");
        }
        if(currencyVO.getRate() == null || currencyVO.getDescription().trim().isEmpty()) {
            errorList.add("Rate cannot be null");
        }
        if (currencyVO.getRateFloat() == null || currencyVO.getRateFloat().compareTo(BigDecimal.ZERO) <= 0) {
            errorList.add("RateFloat cannot be null or less then zero");
        }
        if (currencyVO.getDescription() == null || currencyVO.getDescription().trim().isEmpty()) {
            errorList.add("Description cannot be null or empty");
        }
        if (currencyVO.getSymbol() == null || currencyVO.getSymbol().trim().isEmpty()) {
            errorList.add("Symbol cannot be null or empty");
        }
        if (currencyVO.getCurrencyCode() == null || currencyVO.getCurrencyCode().trim().isEmpty()) {
            errorList.add("CurrencyCode cannot be null or empty");
        }

        if (!errorList.isEmpty()) {
            return errorList;
        }

        // 合理性檢查
        String rateString = currencyVO.getRate().replace(",", "");
        BigDecimal rateFromString = new BigDecimal(rateString).setScale(3, BigDecimal.ROUND_HALF_UP);
        BigDecimal rateFromFloat = currencyVO.getRateFloat().setScale(3, BigDecimal.ROUND_HALF_UP);

        if (!rateFromString.equals(rateFromFloat)) {
            errorList.add("Rate is not equal to RateFloat");
        }



        return errorList;
    }

}
