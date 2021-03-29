package com.dotin.timeOffRequest.exception;

public class BadRequestException extends Exception {
    private String errorCode;
    private String errorMessage;

    public BadRequestException() {
    }

    public BadRequestException(String errorCode, String errorMessage) {
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
