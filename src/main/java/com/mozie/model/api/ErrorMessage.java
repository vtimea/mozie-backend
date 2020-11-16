package com.mozie.model.api;

public class ErrorMessage {
    int errorCode;
    String errorMessage;

    public ErrorMessage(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
