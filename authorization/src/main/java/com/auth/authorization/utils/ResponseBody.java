package com.auth.authorization.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseBody {
    public final static String status_ok = "OK";
    public final static String status_error = "ERROR";

    private String status;
    private Object body;
}
