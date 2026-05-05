package io.github.eggy03.application.exception;

/**
 * Thrown when an error occurs while generating a digest for a public key.
 *
 * <p>Typically wraps lower-level cryptographic exceptions such as
 * {@link java.security.NoSuchAlgorithmException}.</p>
 */
public class KeyDigestException extends RuntimeException {

    @SuppressWarnings("unused")
    public KeyDigestException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("unused")
    public KeyDigestException(String message) {
        super(message);
    }
}
