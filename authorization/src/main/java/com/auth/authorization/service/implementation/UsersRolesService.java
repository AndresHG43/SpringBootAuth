package com.auth.authorization.service.implementation;

import com.auth.authorization.entity.Roles;
import com.auth.authorization.entity.Users;
import com.auth.authorization.repository.UsersRolesRepository;
import com.auth.authorization.service.contract.IUsersRolesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersRolesService implements IUsersRolesService {
    private final UsersRolesRepository usersRolesRepository;

    @Override
    public List<Roles> findRolIdByUsersId(Users usersId) throws RuntimeException  {
        try {
            return usersRolesRepository.findRolIdByUsersId(usersId);
        } catch (Exception e) {
            log.error("[USER] : Error while trying to get User", e);
            throw new RuntimeException ("Error while trying to get User");
        }
    }
}
