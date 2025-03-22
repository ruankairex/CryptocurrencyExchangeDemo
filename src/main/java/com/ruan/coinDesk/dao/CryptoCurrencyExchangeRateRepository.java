package com.ruan.coinDesk.dao;

import com.ruan.coinDesk.model.CoinDeskPOJO.CryptoCurrencyExchangeRatePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CryptoCurrencyExchangeRateRepository extends JpaRepository<CryptoCurrencyExchangeRatePO, Long> {

    /**
     * get data by chartName and currencyCode
     *
     * @param chartName
     * @param currencyCode
     * @return
     */
    List<CryptoCurrencyExchangeRatePO> findByChartNameAndCurrencyCode(String chartName, String currencyCode);
}
