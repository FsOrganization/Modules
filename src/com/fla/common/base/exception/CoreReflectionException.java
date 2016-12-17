package com.fla.common.base.exception;

public class CoreReflectionException extends RuntimeException {

	private static final long serialVersionUID = -6396357641134151148L;

	public CoreReflectionException() {
    }

    public CoreReflectionException(String message) {
        super(message);
    }

    public CoreReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreReflectionException(Throwable cause) {
        super(cause);
    }
}
