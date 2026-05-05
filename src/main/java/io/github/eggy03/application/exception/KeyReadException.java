package io.github.eggy03.application.exception;

/**
 * Thrown when reading a key pair from a source fails.
 *
 * <p>This may occur due to invalid file input, format mismatch,
 * or corrupted key data.</p>
 */
public class KeyReadException extends RuntimeException {

    @SuppressWarnings("unused")
    public KeyReadException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("unused")
    public KeyReadException(String message) {
        super(message);
    }
}
