package com.auth.authorization.utils;

import com.auth.authorization.constants.Errors;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OutMessage {
    //Success messages
    public ResponseBody ok(Object body) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(ResponseBody.status_ok);
        responseBody.setBody(body);
        return responseBody;
    }

    public ResponseBody ok(JSONObject body) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(ResponseBody.status_ok);

        String str = body.toString();
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(str);
        JsonNode objNode = mapper.readTree(parser);

        responseBody.setBody(objNode);
        return responseBody;
    }
    public ResponseBody ok(JSONArray body) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(ResponseBody.status_ok);

        String str = body.toString();
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(str);
        JsonNode objNode = mapper.readTree(parser);

        responseBody.setBody(objNode);
        return responseBody;
    }

    //Error messages
    public ResponseBody error(String code, String message) {
        ResponseBody responseBody = new ResponseBody();
        ResponseBodyError body = new ResponseBodyError(code, message);
        responseBody.setStatus(ResponseBody.status_error);
        responseBody.setBody(body);
        return responseBody;
    }

    public ResponseBody error(String code, Object message) {
        ResponseBody responseBody = new ResponseBody();
        ResponseBodyError body = new ResponseBodyError(code, message);
        responseBody.setStatus(ResponseBody.status_error);
        responseBody.setBody(body);
        return responseBody;
    }

    public ResponseBody error(Object body) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(ResponseBody.status_error);
        responseBody.setBody(body);
        return responseBody;
    }

    public ResponseBody error(JSONObject body) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(ResponseBody.status_error);
        responseBody.setBody(body);
        return responseBody;
    }

    public ResponseBody error(Errors error) {
        ResponseBody responseBody = new ResponseBody();
        ResponseBodyError body = new ResponseBodyError(error.getCode(), error.getMessage());
        responseBody.setStatus(ResponseBody.status_error);
        responseBody.setBody(body);
        return responseBody;
    }
}
