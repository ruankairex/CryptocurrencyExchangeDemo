package com.ruan.coinDesk.model.CoinDeskPOJO;

import java.util.List;

/**
 * This object is used for data conversion within the program
 *
 */
public class CoinDeskDto {

    private String updatedTime;
    private String updatedTimeISO;
    private String updatedTimeUK;
    private String cryptocurrency;
    private String cryptocurrencyChineseName;
    private List<CurrencyRateInfo> currency;
    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedTimeISO() {
        return updatedTimeISO;
    }

    public void setUpdatedTimeISO(String updatedTimeISO) {
        this.updatedTimeISO = updatedTimeISO;
    }

    public String getUpdatedTimeUK() {
        return updatedTimeUK;
    }

    public void setUpdatedTimeUK(String updatedTimeUK) {
        this.updatedTimeUK = updatedTimeUK;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(String cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public String getCryptocurrencyChineseName() {
        return cryptocurrencyChineseName;
    }

    public void setCryptocurrencyChineseName(String cryptocurrencyChineseName) {
        this.cryptocurrencyChineseName = cryptocurrencyChineseName;
    }

    public List<CurrencyRateInfo> getCurrency() {
        return currency;
    }

    public void setCurrency(List<CurrencyRateInfo> currency) {
        this.currency = currency;
    }


    public static class CurrencyRateInfo {
        private String currencyCode;
        private String currencyChineseName;
        private String rate;

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

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoinDeskDto{");
        sb.append("updatedTime='").append(updatedTime).append('\'');
        sb.append(", cryptocurrency='").append(cryptocurrency).append('\'');
        sb.append(", cryptocurrencyChineseName='").append(cryptocurrencyChineseName).append('\'');
        sb.append(", currency=").append(currency);
        sb.append('}');
        return sb.toString();
    }
}
