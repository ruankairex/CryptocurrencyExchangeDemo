package com.ruan.coinDesk.exception;

/**
 * thrown when an error occurs in Expected errors
 *
 */
public class CoinDeskApiException extends RuntimeException{
    public CoinDeskApiException(String message) {
        super(message);
    }
}
