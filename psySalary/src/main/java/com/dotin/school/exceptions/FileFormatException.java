package main.java.com.dotin.school.exceptions;

public class FileFormatException extends Exception {
    private final String errorCode;
    private final String errorMessage;

    public FileFormatException(String errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = this.errorCode + " file does not have a valid format please insert a file by valid format and try again";
    }

    public FileFormatException(String errorCode, String errorMessage) {
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
