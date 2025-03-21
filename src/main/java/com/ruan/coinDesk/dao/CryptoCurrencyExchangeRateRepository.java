package com.ruan.coinDesk.dao;

import com.ruan.coinDesk.model.CoinDeskPOJO.CryptoCurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CryptoCurrencyExchangeRateRepository extends JpaRepository<CryptoCurrencyExchangeRate, Long> {

    List<CryptoCurrencyExchangeRate> findByChartNameAndCurrencyCode(String chartName, String currencyCode);
}
