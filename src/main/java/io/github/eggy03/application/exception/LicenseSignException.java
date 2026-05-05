package io.github.eggy03.application.exception;

/**
 * Thrown when a license signing operation fails.
 *
 * <p>Common causes include invalid cryptographic keys, unsupported algorithms,
 * or encryption-related errors.</p>
 */
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
