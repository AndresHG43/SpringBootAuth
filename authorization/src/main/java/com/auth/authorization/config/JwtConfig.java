package com.auth.authorization.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtConfig {
    // Configuration if keys are stored as PEM files (.key, .crt, etc.). It's more flexible and production-friendly, although more verbose in terms of code.
//    @Value("${jwt.public-key}")
//    private Resource publicKeyResource;
//
//    @Value("${jwt.private-key}")
//    private Resource privateKeyResource;
//
//    @Bean
//    public RSAPublicKey rsaPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        try (InputStream inputStream = publicKeyResource.getInputStream()) {
//            String publicKeyContent = new String(inputStream.readAllBytes())
//                    .replaceAll("\\n", "")
//                    .replace("-----BEGIN PUBLIC KEY-----", "")
//                    .replace("-----END PUBLIC KEY-----", "")
//                    .replaceAll("\\s", "");
//
//            byte[] decoded = Base64.getDecoder().decode(publicKeyContent);
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
//        }
//    }
//
//    @Bean
//    public RSAPrivateKey rsaPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        try (InputStream inputStream = privateKeyResource.getInputStream()) {
//            String privateKeyContent = new String(inputStream.readAllBytes())
//                    .replaceAll("\\n", "")
//                    .replace("-----BEGIN PRIVATE KEY-----", "")
//                    .replace("-----END PRIVATE KEY-----", "")
//                    .replaceAll("\\s", "");
//
//            byte[] decoded = Base64.getDecoder().decode(privateKeyContent);
//            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
//        }
//    }
//
//    @Bean
//    public JwtEncoder jwtEncoder(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
//        JWK jwk = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .build();
//        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
//        return new NimbusJwtEncoder(jwks);
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
//        return NimbusJwtDecoder.withPublicKey(publicKey).build();
//    }

    // Converts a JWT token into an Authentication type object, specifically a JwtAuthenticationToken.
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthorityPrefix("");
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }


    @Value("${jwt.public-key}")
    RSAPublicKey publicKey;
    @Value("${jwt.private-key}")
    RSAPrivateKey privateKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.
                Builder(this.publicKey).
                privateKey(this.privateKey).
                build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    //Automatically publish events using Spring's ApplicationEventPublisher. When authentication succeeds or fails, Spring notifies through this bean.
    @Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher(
            ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
}
