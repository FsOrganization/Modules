package com.fla.common.base.exception;

public class CoreSaveException extends RuntimeException {

	private static final long serialVersionUID = 7468865546682368744L;

	public CoreSaveException() {
    }

    public CoreSaveException(String message) {
        super(message);
    }

    public CoreSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreSaveException(Throwable cause) {
        super(cause);
    }
}
