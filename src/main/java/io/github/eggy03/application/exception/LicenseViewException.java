package io.github.eggy03.application.exception;

/**
 * Thrown when converting or viewing a license representation fails.
 *
 * <p>This is typically related to serialization or formatting errors.</p>
 */
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
