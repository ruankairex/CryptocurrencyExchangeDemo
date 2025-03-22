package com.ruan.coinDesk.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines currency and crypto currency map , can be used to get currency chinese name
 * and can use this map to validate currency name is legal
 */
public class CurrencyMap {
    public static final Map<String, String> CURRENCY_MAP;
    public static final Map<String, String> CRYPTO_CURRENCY_MAP;

    static {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("USD", "美元");
        tempMap.put("EUR", "歐元");
        tempMap.put("GBP", "英鎊");
        CURRENCY_MAP = Collections.unmodifiableMap(tempMap);
    }

    static {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("Bitcoin", "比特幣");
        CRYPTO_CURRENCY_MAP = Collections.unmodifiableMap(tempMap);
    }

    public static String getCurrencyName(String currencyCode) {
        return CURRENCY_MAP.get(currencyCode);
    }

    public static String getCryptoCurrencyName(String currencyCode) {
        return CRYPTO_CURRENCY_MAP.get(currencyCode);
    }

    public static boolean containsCurrency(String currencyCode) {
        return CURRENCY_MAP.containsKey(currencyCode);
    }

    public static boolean containsCryptoCurrency(String currencyCode) {
        return CRYPTO_CURRENCY_MAP.containsKey(currencyCode);
    }
}
