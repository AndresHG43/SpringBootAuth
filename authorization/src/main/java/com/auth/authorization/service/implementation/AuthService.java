package com.auth.authorization.service.implementation;

import com.auth.authorization.filter.LoginDto;
import com.auth.authorization.filter.PasswordDto;
import com.auth.authorization.filter.RefreshTokenDto;
import com.auth.authorization.repository.UsersRepository;
import com.auth.authorization.service.contract.IAuthService;
import com.auth.authorization.service.contract.ITokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService implements IAuthService, UserDetailsService {
    private final UsersRepository usersRepository;
    private final ITokenService tokenService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final PasswordEncoder passwordEncoder;

    @Override
    public HashMap<String, Object> login(final LoginDto loginDto) {
        try {
            log.info("Login attempt from {}", loginDto.getEmail());

            //validates that the user exists and is active
            loadUserByUsername(loginDto.getEmail());

            final AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
            final Authentication authRequest = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            final Authentication authentication = authenticationManager.authenticate(authRequest);
            final String jwt = tokenService.generateToken(authentication, null);
            final String refreshToken = tokenService.generateToken(authentication, "REFRESH_TOKEN");

            HashMap<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("refreshToken", refreshToken);
            response.put("username", loginDto.getEmail());
            return response;

        } catch (Exception e) {
            log.error("[USER] : Error while trying to login.   ", e);
            throw new ProviderNotFoundException("Error while trying to login");
        }
    }

    @Override
    public HashMap<String, Object> refreshToken(final RefreshTokenDto refreshTokenDto) {
        try {
            log.info("Refresh token attempt from {}", refreshTokenDto.getEmail());

            //validates that the user exists and is active
            final UserDetails user = loadUserByUsername(refreshTokenDto.getEmail());

            final String username = tokenService.getClaim(refreshTokenDto.getRefreshToken(), "username");
            if (!username.equals(refreshTokenDto.getEmail())) {
                log.error("[USER] : Error while trying to refresh token. The user does not correspond to the token");
                throw new ProviderNotFoundException("Error while trying to refresh token. The user does not correspond to the token");
            }

            final Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            final String jwt = tokenService.generateToken(authentication, null);
            final String refreshToken = tokenService.generateToken(authentication, "REFRESH_TOKEN");

            HashMap<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("refreshToken", refreshToken);
            response.put("username", refreshTokenDto.getEmail());
            return response;

        } catch (Exception e) {
            log.error("[USER] : Error while trying to refresh token. ", e);
            throw new ProviderNotFoundException("Error while trying to refresh token");
        }
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return usersRepository.findUserByEmailAndActiveTrue(username)
                .orElseThrow(() -> {
                    log.error("[USER] : User not found with email {}", username);
                    return new UsernameNotFoundException("User not found");
                });
    }

    @Override
    public void logout(Authentication authentication) {
        authentication.setAuthenticated(false);
        log.info("[LOGOUT] User has successfully logged out.");

        SecurityContextHolder.clearContext();
    }

    @Override
    public String generatePassword (final PasswordDto passwordDto) {
        try {
            return passwordEncoder.encode(passwordDto.getPassword());
        } catch (RuntimeException e) {
            log.error("[USER] : Error while trying to generate password. ", e);
            throw new RuntimeException("Error while trying to generate password");
        }
    }
}
