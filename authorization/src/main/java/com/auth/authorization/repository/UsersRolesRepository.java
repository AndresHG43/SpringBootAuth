package com.auth.authorization.repository;

import com.auth.authorization.entity.Roles;
import com.auth.authorization.entity.Users;
import com.auth.authorization.entity.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRolesRepository extends JpaRepository<UsersRoles, Long> {
    List<Roles> findRolIdByUsersId(Users usersId);
}
