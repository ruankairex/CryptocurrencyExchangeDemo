package com.ruan.coinDesk.model.CoinDeskPOJO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CoindeskApiResponse {

    private TimeInfo time;
    private String disclaimer;
    private String chartName;
    private Map<String, BpiInfo> bpi;


    public TimeInfo getTime() { return time; }
    public void setTime(TimeInfo time) { this.time = time; }

    public String getDisclaimer() { return disclaimer; }
    public void setDisclaimer(String disclaimer) { this.disclaimer = disclaimer; }

    public String getChartName() { return chartName; }
    public void setChartName(String chartName) { this.chartName = chartName; }

    public Map<String, BpiInfo> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, BpiInfo> bpi) {
        this.bpi = bpi;
    }

    public static class TimeInfo {
        private String updated;
        private String updatedISO;
        @JsonProperty("updateduk")
        private String updatedUK;

        public String getUpdated() { return updated; }
        public void setUpdated(String updated) { this.updated = updated; }

        public String getUpdatedISO() { return updatedISO; }
        public void setUpdatedISO(String updatedISO) { this.updatedISO = updatedISO; }

        public String getUpdatedUK() {
            return updatedUK;
        }

        public void setUpdatedUK(String updatedUK) {
            this.updatedUK = updatedUK;
        }
    }

    public static class BpiInfo {
        private String code;
        private String symbol;
        private String rate;
        private String description;
        @JsonProperty("rate_float")
        private double rateFloat;

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }

        public String getSymbol() { return symbol; }
        public void setSymbol(String symbol) { this.symbol = symbol; }

        public String getRate() { return rate; }
        public void setRate(String rate) { this.rate = rate; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public double getRateFloat() {
            return rateFloat;
        }

        public void setRateFloat(double rateFloat) {
            this.rateFloat = rateFloat;
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoindeskResponse{");
        sb.append("time=").append(time);
        sb.append(", disclaimer='").append(disclaimer).append('\'');
        sb.append(", chartName='").append(chartName).append('\'');
        sb.append(", bpi=").append(bpi);
        sb.append('}');
        return sb.toString();
    }
}





