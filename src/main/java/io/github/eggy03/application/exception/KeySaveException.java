package io.github.eggy03.application.exception;

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
