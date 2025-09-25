package com.auth.authorization.constants;

public enum Errors {
    //Custom errors
    COD_801_ERROR_NOT_CATALOGED("801","Error not cataloged"),
    COD_802_NO_FILE("802","The file was not found.");

    private final String code;
    private final String message;


    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    private Errors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static class errors{
        public static final String COD_801_ERROR_NOT_CATALOGED = "801";
        public static final String COD_802_NO_FILE = "802";
    }
}
