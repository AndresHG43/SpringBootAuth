package com.auth.authorization.service.contract;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ITokenService {
    String generateToken(Authentication authentication, String scope);
    Jwt getJwt(String token);
    String getClaim(String token, String claim);
    String getUserFromToken(String token);
    boolean validateToken(String token);
}
