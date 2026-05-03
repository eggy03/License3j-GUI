package io.github.eggy03.application.exception;

public class LicenseSignException extends RuntimeException {

    @SuppressWarnings("unused")
    public LicenseSignException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("unused")
    public LicenseSignException(String message) {
        super(message);
    }
}
