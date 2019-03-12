package com.gmail.erofeev.st.alexei.myonlineshop.repository.exception;

public class RepositoryException extends Exception {
    public RepositoryException() {
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
