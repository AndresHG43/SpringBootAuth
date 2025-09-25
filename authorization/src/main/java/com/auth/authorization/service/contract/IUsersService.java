package com.auth.authorization.service.contract;

import com.auth.authorization.entity.Users;
import com.auth.authorization.entity.dto.UsersDto;

import java.util.Optional;

public interface IUsersService {
    long getCantUsers() throws RuntimeException;
    Users createFirstUser(final UsersDto createUsersDto) throws RuntimeException;
    Optional<Users> getUserByEmail(String email) throws RuntimeException;
}
