package io.github.eggy03.application.exception;

/**
 * Thrown when a key pair generation operation fails.
 *
 * <p>This usually indicates an invalid or unsupported cryptographic algorithm
 * or configuration.</p>
 */
public class KeyGenerationException extends RuntimeException {

    @SuppressWarnings("unused")
    public KeyGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("unused")
    public KeyGenerationException(String message) {
        super(message);
    }
}
