package com.auth.authorization.entity.mapper;

import com.auth.authorization.entity.Roles;
import com.auth.authorization.entity.dto.RolesDto;

public class RolesMapper {
    public void updateRoles(RolesDto source, Roles destination) {
        if ( source == null ) {
            return;
        }

        if ( source.getName() != null ) {
            destination.setName( source.getName() );
        }
        if ( source.getDescription() != null ) {
            destination.setDescription( source.getDescription() );
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
