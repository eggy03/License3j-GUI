package io.github.eggy03.application.exception;

public class LicenseViewException extends RuntimeException {

    @SuppressWarnings("unused")
    public LicenseViewException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("unused")
    public LicenseViewException(String message) {
        super(message);
    }
}
