package io.github.eggy03.application.exception;


public class LicenseSaveException extends RuntimeException {

    @SuppressWarnings("unused")
    public LicenseSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("unused")
    public LicenseSaveException(String message) {
        super(message);
    }
}
