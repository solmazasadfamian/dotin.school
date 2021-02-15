package com.dotin.school.exceptions;

public class DepositNotFoundException extends Exception {
    private String errorCode;
    private String errorMessage;

    public DepositNotFoundException(String errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = "depositNumber: " + errorCode + " not found in the balance file";
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
