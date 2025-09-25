package com.auth.authorization.entity.dto;

import com.auth.authorization.entity.Roles;
import com.auth.authorization.entity.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesDto {
    @NotBlank
    @Size(max = 100, message = "{error.roles.name.max}")
    private String name;
    @NotBlank
    @Size(max = 255, message = "{error.roles.description.max}")
    private String description;

    private Users usersCreated;
    private Users usersUpdated;
    private Users usersDeleted;

    public Roles toEntity(){
        Roles roles = new Roles();
        roles.setName(name);
        roles.setDescription(description);
        roles.setUsersCreated(usersCreated);
        roles.setUsersUpdated(usersUpdated);
        roles.setUsersDeleted(usersDeleted);
        return roles;
    }
}
