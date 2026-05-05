package io.github.eggy03.application.exception;

/**
 * Thrown when saving a license to a destination fails.
 *
 * <p>This may be caused by invalid file paths, insufficient permissions,
 * or underlying I/O errors.</p>
 */
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
