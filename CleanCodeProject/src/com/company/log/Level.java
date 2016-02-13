package com.company.log;

public enum Level {
    EXCEPTION,
    METHOD,
    ERROR;

    @Override
    public String toString() {
        switch (this) {
            case EXCEPTION:
                return Constants.EXCEPTION;
            case METHOD:
                return Constants.METHOD;
            case ERROR:
                return Constants.ERROR;
            default:
                return Constants.ERROR;
        }
    }

    private static class Constants {
        public static final String EXCEPTION = "[EXCEPTION]";
        public static final String METHOD = "[METHOD]";
        public static final String ERROR = "[ERROR]";
    }

}
