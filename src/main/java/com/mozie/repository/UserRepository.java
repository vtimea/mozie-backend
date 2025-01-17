package com.mozie.repository;

import com.mozie.model.database.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserByToken(String token);

    User findUserByUserId(String userId);
}