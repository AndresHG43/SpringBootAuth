package com.auth.authorization.service.implementation;

import com.auth.authorization.entity.Users;
import com.auth.authorization.entity.dto.UsersDto;
import com.auth.authorization.repository.UsersRepository;
import com.auth.authorization.service.contract.IUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService implements IUsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public long getCantUsers() throws RuntimeException  {
        try {
            return usersRepository.countUsersByActiveTrue();
        } catch (Exception e) {
            log.error("[USER] : Error trying to get the number of users. ", e);
            throw new RuntimeException ("Error trying to get the number of users");
        }
    }

    @Override
    public Users createFirstUser(final UsersDto createUsersDto) throws RuntimeException {
        try {
            final Users createUsers = createUsersDto.toEntity();
            createUsers.setPassword(passwordEncoder.encode(createUsersDto.getPassword()));
            final Users users = usersRepository.save(createUsers);
            log.info("[USER] : User successfully created with id {}", users.getId());
            return users;
        } catch (Exception e) {
            log.error("[USER] : Error trying to create user. ", e);
            throw new RuntimeException ("Error trying to create user");
        }

    }

    @Override
    public Optional<Users> getUserByEmail(String email) throws RuntimeException  {
        try {
            return usersRepository.findUserByEmailAndActiveTrue(email);
        } catch (Exception e) {
            log.error("[USER] : Error while trying to get User. ", e);
            throw new RuntimeException ("Error while trying to get User");
        }
    }
}
