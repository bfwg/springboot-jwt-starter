package com.bfwg.remote.repository;

import com.bfwg.remote.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(String username );

    UserEntity findByEmail(String email);
}

