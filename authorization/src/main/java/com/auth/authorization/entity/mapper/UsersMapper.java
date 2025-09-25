package com.auth.authorization.entity.mapper;

import com.auth.authorization.entity.Users;
import com.auth.authorization.entity.dto.UsersDto;

public class UsersMapper {
    public void updateUser(final UsersDto source, Users destination) {
        if ( source == null ) {
            return;
        }

        if ( source.getName() != null ) {
            destination.setName( source.getName() );
        }
        if ( source.getLastname() != null ) {
            destination.setLastname( source.getLastname() );
        }
        if ( source.getEmail() != null ) {
            destination.setEmail( source.getEmail() );
        }
    }
}
