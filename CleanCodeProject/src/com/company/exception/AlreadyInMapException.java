package com.company.exception;

import com.company.log.Level;
import com.company.log.Log;

public class AlreadyInMapException extends Exception {
    public AlreadyInMapException() {
        super();
    }

    public AlreadyInMapException(String s) {
        Log.write(s, Level.EXCEPTION);
    }

    public AlreadyInMapException(String s, Throwable throwable) {
        Log.write(throwable.getClass().getName() + ": " + s, Level.EXCEPTION);
    }

    public AlreadyInMapException(Throwable throwable) {
        super(throwable);
    }

    protected AlreadyInMapException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
