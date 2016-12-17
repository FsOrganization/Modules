package com.fla.common.base.exception;

public class CoreQueryException extends RuntimeException {

	private static final long serialVersionUID = 5538699323094258337L;

	public CoreQueryException() {
    }

    public CoreQueryException(String message) {
        super(message);
    }

    public CoreQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreQueryException(Throwable cause) {
        super(cause);
    }
}
