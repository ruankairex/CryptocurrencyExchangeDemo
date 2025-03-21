package com.ruan.coinDesk.model.CoinDeskPOJO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CryptocurrencyExchangeRateRequest {

    private String chartName;
    private String currencyCode;
    private String rate;
    private String description;
    private String symbol;
    @JsonProperty("rate_float")
    private BigDecimal rateFloat;

    private String currencyChineseName;

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(BigDecimal rateFloat) {
        this.rateFloat = rateFloat;
    }

    public String getCurrencyChineseName() {
        return currencyChineseName;
    }

    public void setCurrencyChineseName(String currencyChineseName) {
        this.currencyChineseName = currencyChineseName;
    }
}
