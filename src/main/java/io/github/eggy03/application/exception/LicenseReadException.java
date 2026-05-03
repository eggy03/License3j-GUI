package io.github.eggy03.application.exception;

public class LicenseReadException extends RuntimeException {

    @SuppressWarnings("unused")
    public LicenseReadException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("unused")
    public LicenseReadException(String message) {
        super(message);
    }
}
