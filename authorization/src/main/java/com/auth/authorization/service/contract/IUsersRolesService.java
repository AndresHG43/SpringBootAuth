package com.auth.authorization.service.contract;

import com.auth.authorization.entity.Roles;
import com.auth.authorization.entity.Users;

import java.util.List;

public interface IUsersRolesService {
    List<Roles> findRolIdByUsersId(Users usersId) throws RuntimeException ;
}
