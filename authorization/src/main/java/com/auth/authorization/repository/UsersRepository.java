package com.auth.authorization.repository;

import com.auth.authorization.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    long countUsersByActiveTrue();
    Optional<Users> findByEmail(String email);
    Optional<Users> findUserByEmailAndActiveTrue(String email);
}
