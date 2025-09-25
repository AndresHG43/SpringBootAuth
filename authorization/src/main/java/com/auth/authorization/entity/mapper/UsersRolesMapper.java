package com.auth.authorization.entity.mapper;

import com.auth.authorization.entity.UsersRoles;
import com.auth.authorization.entity.dto.UsersRolesDto;

public class UsersRolesMapper {
    public void updateUserRoles(UsersRolesDto source, UsersRoles destination) {
        if ( source == null ) {
            return;
        }

        if ( source.getUsersId() != null ) {
            destination.setUsersId( source.getUsersId() );
        }
        if ( source.getRoleId() != null ) {
            destination.setRoleId( source.getRoleId() );
        }
        if ( source.getUsersCreated() != null ) {
            destination.setUsersCreated( source.getUsersCreated() );
        }
        if ( source.getUsersUpdated() != null ) {
            destination.setUsersUpdated( source.getUsersUpdated() );
        }
        if ( source.getUsersDeleted() != null ) {
            destination.setUsersDeleted( source.getUsersDeleted() );
        }
    }
}
