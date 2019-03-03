package com.erofeev.st.alexei.myonlineshop.servlet.command.exception;

public class CommandNotFound extends Exception {
    public CommandNotFound() {
    }

    public CommandNotFound(String message) {
        super(message);
    }

    public CommandNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandNotFound(Throwable cause) {
        super(cause);
    }

    public CommandNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
