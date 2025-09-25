package com.auth.authorization.config;

public class ApiConfig {
    private static final String COMMON_PATH = "/application/api";
    private static final String API_VERSION = "/v1";
    public static final String API_BASE_PATH = COMMON_PATH + API_VERSION;

    private ApiConfig() {
        throw new UnsupportedOperationException("This class should never be instantiated");
    }
}
