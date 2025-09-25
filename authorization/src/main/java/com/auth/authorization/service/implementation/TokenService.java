package com.auth.authorization.service.implementation;

import com.auth.authorization.entity.Users;
import com.auth.authorization.service.contract.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TokenService implements ITokenService {
    @Value("${jwt.access-token-expiration}")
    private int jwtExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private int jwtRefreshExpiration;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public String generateToken(Authentication authentication, String scope) {
        Instant now = Instant.now();
        int tokenDuration = jwtRefreshExpiration;

        if (scope == null) {
            tokenDuration = jwtExpiration;
            scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
        }

        Users currentUsers = (Users) authentication.getPrincipal();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(currentUsers.getEmail())
                .issuedAt(now)
                .expiresAt(now.plus(tokenDuration, ChronoUnit.MINUTES))
                .claim("scope", scope)
                .claim("username", currentUsers.getEmail())
                .build();

        var jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(SignatureAlgorithm.RS256).build(), claims);
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }

    @Override
    public Jwt getJwt(String token) {
        return this.jwtDecoder.decode(token);
    }

    @Override
    public String getClaim(String token, String claim) {
        return this.getJwt(token).getClaimAsString(claim);
    }

    @Override
    public String getUserFromToken(String token) {
        Jwt jwtToken = jwtDecoder.decode(token);
        return jwtToken.getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtValidationException e) {
            if (e.getMessage() != null && e.getMessage().contains("Jwt expired")) {
                log.warn("[USER] : Token expired", e);
                throw new CredentialsExpiredException("Token has expired", e);
            }
            log.error("[USER] : Invalid JWT token", e);
            throw new InvalidBearerTokenException("Invalid JWT token", e);
        } catch (Exception e) {
            log.error("[USER] : Unexpected error while validating token", e);
            throw new BadJwtException("Unexpected error while validating token");
        }
    }
}
