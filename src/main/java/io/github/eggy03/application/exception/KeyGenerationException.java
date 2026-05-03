package io.github.eggy03.application.exception;

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
