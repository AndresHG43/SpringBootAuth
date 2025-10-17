package com.auth.authorization.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users implements UserDetails, Cloneable {
    @Id
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    @GeneratedValue(generator = "users_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(length = 80, nullable = false)
    private String name;
    @Column(length = 80, nullable = false)
    private String lastname;
    @Column(length = 100, unique = true, nullable = false)
    private String email;
    @Column(length = 500, nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "usersId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UsersRoles> usersRoles;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;
    @Column(name = "date_deleted")
    private LocalDateTime dateDeleted;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        try {
            if (usersRoles == null || usersRoles.isEmpty()) {
                return Collections.emptyList();
            }

            return usersRoles.stream()
                    .filter(UsersRoles::isActive) // Only active UserRoles
                    .map(UsersRoles::getRoleId)   // Get the Roles object
                    .filter(Roles::isActive)     // Only active roles
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase())) // Spring Security looking for an authority with the prefix "ROLE_",
                    .toList();
        } catch (Exception e) {
            System.out.println("Error getting collection: " + e);
            return Collections.emptyList();
        }
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
