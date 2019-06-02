package com.durex.lauchat.exception;

public class LauChatException extends RuntimeException {

    public LauChatException() {
        super();
    }

    public LauChatException(String message) {
        super(message);
    }

    public LauChatException(String message, Throwable cause) {
        super(message, cause);
    }

    public LauChatException(Throwable cause) {
        super(cause);
    }

    protected LauChatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
