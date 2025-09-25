package com.auth.authorization.entity.dto;

import com.auth.authorization.entity.Roles;
import com.auth.authorization.entity.UsersRoles;
import com.auth.authorization.entity.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersRolesDto {
    @NotBlank
    @NotNull
    private Users usersId;
    @NotBlank
    @NotNull
    private Roles roleId;

    private Users usersCreated;
    private Users usersUpdated;
    private Users usersDeleted;

    public UsersRoles toEntity(){
        UsersRoles usersRoles = new UsersRoles();
        usersRoles.setUsersId(usersId);
        usersRoles.setRoleId(roleId);
        usersRoles.setUsersCreated(usersCreated);
        usersRoles.setUsersUpdated(usersUpdated);
        usersRoles.setUsersDeleted(usersDeleted);
        return usersRoles;
    }
}
