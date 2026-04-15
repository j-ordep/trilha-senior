package org.example;

public class NoStackTraceException extends RuntimeException {

    public NoStackTraceException(String message) {
        this(message, null);
    }

    public NoStackTraceException(String message, Throwable cause) {
        // desativa o enableSuppression (coleta de exceções secundarias) e writableStackTrace (monta a stackTrace causando sobrecarga no GC)
        super(message, null, false, false);
    }


}
