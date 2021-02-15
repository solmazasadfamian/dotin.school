package com.dotin.school.exceptions;

public class NotEnoughBalanceException extends Exception {
    private String errorCode;
    private String errorMessage;

    public NotEnoughBalanceException() {
    }

    public NotEnoughBalanceException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
