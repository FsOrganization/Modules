package com.fla.common.util;

public class CoreReflectionException extends RuntimeException {

	private static final long serialVersionUID = -7157514020793707148L;

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
