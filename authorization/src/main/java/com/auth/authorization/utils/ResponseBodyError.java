package com.auth.authorization.utils;

import lombok.Data;

@Data
public class ResponseBodyError {
    private final static long serialVersionUID = 985885270535689192L;

    private String code;
    private Object message;

    public ResponseBodyError(String code, Object message){
        this.code = code;
        this.message = message;
    }

    public ResponseBodyError(String code, String message){
        this.code = code;
        this.message = message;
    }
}
