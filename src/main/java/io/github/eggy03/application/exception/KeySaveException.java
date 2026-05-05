package io.github.eggy03.application.exception;

/**
 * Thrown when saving a key pair to a destination fails.
 *
 * <p>This can happen due to invalid file paths, permission issues,
 * or I/O failures.</p>
 */
public class KeySaveException extends RuntimeException {

    @SuppressWarnings("unused")
    public KeySaveException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("unused")
    public KeySaveException(String message) {
        super(message);
    }
}
