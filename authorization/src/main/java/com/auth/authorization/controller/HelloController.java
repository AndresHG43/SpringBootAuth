package com.auth.authorization.controller;

import com.auth.authorization.config.ApiConfig;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConfig.API_BASE_PATH + "/hello-world")
public class HelloController extends Controller {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROOT')")
    public ResponseEntity<?> helloWorld() {
        return ResponseEntity
                .ok(
                        message.ok("Hello world")
                );
    }
}
