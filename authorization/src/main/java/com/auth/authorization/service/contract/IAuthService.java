package com.auth.authorization.service.contract;

import com.auth.authorization.filter.LoginDto;
import com.auth.authorization.filter.PasswordDto;
import com.auth.authorization.filter.RefreshTokenDto;
import org.springframework.security.core.Authentication;

import java.util.HashMap;

public interface IAuthService {
    HashMap<String, Object> login(LoginDto loginDto);
    HashMap<String, Object> refreshToken(final RefreshTokenDto refreshTokenDto);
    void logout(Authentication authentication);
    String generatePassword (final PasswordDto passwordDto) throws RuntimeException;
}
