package com.ruan.coinDesk.exception;

public class CoinDeskApiException extends RuntimeException{
    public CoinDeskApiException(String message) {
        super(message);
    }

    public CoinDeskApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
