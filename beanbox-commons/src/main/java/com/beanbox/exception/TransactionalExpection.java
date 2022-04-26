package com.beanbox.exception;

public class TransactionalExpection extends RuntimeException{
    public TransactionalExpection() {
    }

    public TransactionalExpection(String message) {
        super(message);
    }

    public TransactionalExpection(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionalExpection(Throwable cause) {
        super(cause);
    }

    public TransactionalExpection(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
