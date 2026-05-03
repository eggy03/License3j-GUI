package io.github.eggy03.application.exception;

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
