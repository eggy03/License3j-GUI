package io.github.eggy03.application.exception;

/**
 * Thrown when reading a license from a source fails.
 *
 * <p>This typically wraps I/O errors or invalid license format issues.</p>
 */
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
