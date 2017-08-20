package com.example.demo.model.exception;

public class DuplicateEntryException extends BusinessException {
    public DuplicateEntryException() {
        super();
    }

    public DuplicateEntryException(String s) {
        super(s);
    }

    public DuplicateEntryException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DuplicateEntryException(Throwable throwable) {
        super(throwable);
    }

    protected DuplicateEntryException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
