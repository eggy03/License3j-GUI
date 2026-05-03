package io.github.eggy03.application.exception;

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
