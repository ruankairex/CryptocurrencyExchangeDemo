package com.ruan.coinDesk.model.CoinDeskPOJO;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_currency_exchange_rates")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoCurrencyExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "chart_name", nullable = false, length = 20)
    private String chartName;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "currency_chinese_name", length = 50)
    private String currencyChineseName;

    @Column(name = "currency_symbol", length = 10)
    private String currencySymbol;

    @Column(name = "rate", length = 50)
    private String rate;

    @Column(name = "rate_float", precision = 15, scale = 4)
    private BigDecimal rateFloat;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "last_updated", nullable = false, updatable = false)
    private String lastUpdated;

    // Getter and Setter methods

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyChineseName() {
        return currencyChineseName;
    }

    public void setCurrencyChineseName(String currencyChineseName) {
        this.currencyChineseName = currencyChineseName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public BigDecimal getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(BigDecimal rateFloat) {
        this.rateFloat = rateFloat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
